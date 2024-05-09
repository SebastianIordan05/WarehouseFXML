package com.vm.valorizzazionemagazzinofxml;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
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

        setValori();
        calcolaGiacenza(Articolo.getArticoli());

        launch();
    }

    private static void leggiDati(String filePath, int numCampi, Function<String[], Object> creator) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine();
            br.lines().forEach(line -> {
                String[] data = line.split(LINE_SPLIT);
                if (data.length >= numCampi) {
                    Object obj = creator.apply(data);
                    System.out.println(obj.toString());
                }
            });
        } catch (IOException e) {
        }
    }

    private static void causaliLst() {
        leggiDati(CSV_FILE_CAUSALI, 3, data -> new Causale(Integer.parseInt(data[0]), data[1], data[2]));
    }

    private static void articoliLst() {
        leggiDati(CSV_FILE_ARTICOLI, 3, data -> new Articolo(Integer.parseInt(data[0]), data[1], data[2]));
    }

    private static void movimentiLst() {
        leggiDati(CSV_FILE_MOVIMENTI, 7, data -> new Movimento(Integer.parseInt(data[0]), Integer.parseInt(data[1]),
                Integer.parseInt(data[2]), data[3], Double.parseDouble(data[4]), Double.parseDouble(data[5]),
                Integer.parseInt(data[6])));
    }

    private static void movimentiPerArticolo() {
        Map<Integer, Integer> counts = new HashMap<>();
        for (Movimento m : Movimento.getMovimenti().values()) {
            counts.put(m.getArtico(), counts.getOrDefault(m.getArtico(), 0) + 1);
        }
        counts.forEach((id, count) -> System.out.println("Movimenti fatti per articolo con id " + id + ": " + count));
    }

    public static Map<String, Integer> conteggioArticoli(String filePath) {
        Map<String, Integer> counts = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine();
            br.lines().forEach(line -> {
                String descrizione = line.split(LINE_SPLIT)[1];
                counts.put(descrizione, counts.getOrDefault(descrizione, 0) + 1);
            });
        } catch (IOException e) {
        }
        return counts;
    }

    public static void calcolaGiacenza(Map<Integer, Articolo> articoli) {
        for (Articolo articolo : articoli.values()) {
            int giacenza = contaArticoliConStessaDescrizione(articoli, articolo.getDescrizione());
            articolo.setGiacenza(giacenza);
        }
    }

    private static int contaArticoliConStessaDescrizione(Map<Integer, Articolo> articoli, String descrizione) {
        int count = 0;
        for (Articolo articolo : articoli.values()) {
            if (articolo.getDescrizione().equals(descrizione)) {
                count++;
            }
        }
        return count;
    }

    private static void setValori() {
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE_MOVIMENTI))) {
            br.readLine();
            br.lines().forEach(line -> {
                String[] data = line.split(LINE_SPLIT);
                int idArticolo = Integer.parseInt(data[0]);
                double valore = Double.parseDouble(data[5]);
                Articolo articolo = Articolo.getArticoli().get(idArticolo);
                if (articolo != null) {
                    articolo.setValore(valore);
                }
            });
        } catch (IOException e) {
        }
    }

}
