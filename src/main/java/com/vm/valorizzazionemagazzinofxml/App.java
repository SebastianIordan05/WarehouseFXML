package com.vm.valorizzazionemagazzinofxml;

import java.io.BufferedReader;
import java.io.FileReader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import model.Articolo;
import model.Causale;
import model.Movimento;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static final String CSV_FILE_ARTICOLI = "./magazzino/articoli.csv";
    private static final String CSV_FILE_CAUSALI = "./magazzino/casuali.csv";
    private static final String CSV_FILE_MOVIMENTI = "./magazzino/movimenti.csv";
    private static final String LINE_SPLIT = ";";
    private static Articolo a;
    
    private static final Map<String, Integer> conteggio = conteggioArticoli(CSV_FILE_ARTICOLI);

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"));
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        articoliLst();
        movimentiLst();
        causaliLst();
        System.out.println(Articolo.getArticoli());
        System.out.println(Causale.getCausali());
        System.out.println(Movimento.getMovimenti());
        System.out.println("movimenti.size() " + Movimento.getMovimenti().size());
        System.out.println("causali.size() " + Causale.getCausali().size());
        System.out.println("articoli.size() " + Articolo.getArticoli().size());
        movimentiPerArticolo();
        
        for (Map.Entry<String, Integer> entry : conteggio.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        
//        launch();
    }

    private static void causaliLst() {
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE_CAUSALI))) {
            br.readLine();
            br.lines().forEach(line -> {
                String[] data = line.split(LINE_SPLIT);
                if (data.length >= 3) { // Assicurati che ci siano almeno 3 campi nella riga
                    Causale c = new Causale(Integer.parseInt(data[0]), data[1], data[2]);
                    System.out.println(c.toString());
                }
            });
        } catch (IOException e) {
        }
    }
    
    private static void articoliLst() {
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE_ARTICOLI))) {
            br.readLine();
            br.lines().forEach(line -> {
                String[] data = line.split(LINE_SPLIT);
                if (data.length >= 3) { // Assicurati che ci siano almeno 3 campi nella riga
                    Articolo a = new Articolo(Integer.parseInt(data[0]), data[1], data[2]);
                    System.out.println(a.toString());
                }
            });
        } catch (IOException e) {
        }
    }

    private static void movimentiLst() {
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE_MOVIMENTI))) {
            br.readLine();
            br.lines().forEach(line -> {
                String[] data = line.split(LINE_SPLIT);
                if (data.length >= 7) { // Assicurati che ci siano almeno 3 campi nella riga
                    Movimento m = new Movimento(Integer.parseInt(data[0]), Integer.parseInt(data[1]),
                            Integer.parseInt(data[2]), data[3], Double.parseDouble(data[4]),
                            Double.parseDouble(data[5]), Integer.parseInt(data[6]));
                    System.out.println(m.toString());
                }
            });
        } catch (IOException e) {
        }
    }
    
    private static void movimentiPerArticolo() {
        int itemNumber = 0;
        for (int i = 1; i <= Articolo.getArticoli().size(); i++) {
            for (Movimento m : Movimento.getMovimenti().values()) {
                if (m.getArtico() == i)
                    itemNumber++;
            }
            System.out.println("movimenti fatti per articolo con id " + i + ": " + itemNumber);
            itemNumber = 0;
        }
    }
    
    public static Map<String, Integer> conteggioArticoli(String filePath) {
        Map<String, Integer> c = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine();

            String riga;
            while ((riga = br.readLine()) != null) {
                String[] colonne = riga.split(LINE_SPLIT);

                String descrizione = colonne[1];

                c.put(descrizione, c.getOrDefault(descrizione, 0) + 1);
            }
        } catch (IOException e) {
        }

        return c;
    }
}