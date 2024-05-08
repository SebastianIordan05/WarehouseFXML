package com.vm.valorizzazionemagazzinofxml;

import java.io.BufferedReader;
import java.io.FileReader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
    private static final String LINE = "";
    private static final String LINE_SPLIT = ";";

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"), 640, 480);
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
        launch();
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
                    Movimento m = new Movimento(Integer.parseInt(data[0]), data[1],
                            Integer.parseInt(data[2]), data[3], Double.parseDouble(data[4]),
                            Double.parseDouble(data[5]), Integer.parseInt(data[6]));
                    System.out.println(m.toString());
                }
            });
        } catch (IOException e) {
        }
    }

}
