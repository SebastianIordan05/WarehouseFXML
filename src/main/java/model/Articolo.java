/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author seba2
 */
public class Articolo implements Serializable {
    
    private final int codice;
    private final String descrizione;
    private int ubicazione;
    private final String unità;
    
    private static final String FILE = "./articoli.dat";
    private static final Map<Integer, Articolo> articoli = loadArticoli(new File(FILE));

    public Articolo(int codice, String descrizione, String unità) {
        this.codice = codice;
        this.unità = unità;
        this.descrizione = descrizione;
        articoli.put(codice, this);
    }

    public int getCodice() {
        return codice;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public int getUbicazione() {
        return ubicazione;
    }

    public String getUnità() {
        return unità;
    }

    public static String getFILE() {
        return FILE;
    }

    public static Map<Integer, Articolo> getArticoli() {
        return articoli;
    }
    
    public void putArticolo(Articolo a) {
        articoli.put(a.getCodice(), a);
    }
    
    public void removeArticolo(int c) {
        articoli.remove(c);
    }

    @Override
    public String toString() {
        return "Articolo{" + "codice=" + codice + ", descrizione=" + descrizione + ", ubicazione=" + ubicazione + ", unit\u00e0=" + unità + '}';
    }
    
    private static Map<Integer, Articolo> loadArticoli(final File f) {
        try {
            if (!f.exists()) {
                f.createNewFile();
                return new HashMap<>();
            }
            
            if (!f.canRead()) {
                return new HashMap<>();
            }
            
            final ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(f));
            final Map<Integer, Articolo> articolo = (Map<Integer, Articolo>) inputStream.readObject();
            
            return articolo;

        } catch (final IOException | ClassNotFoundException ex) {
        }

        return new HashMap<>();
    }
    
    public static void saveArticoli(final Map<Integer, Articolo> articolo, final File f) {
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            
            if (!f.canWrite()) {
                return;
            }
            
            final ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(f));
            outputStream.writeObject(articolo);
        } catch (final IOException ex) {
        }
    }
}
