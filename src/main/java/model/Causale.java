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
public class Causale implements Serializable {
    
    private final int codice;
    
    private final String descrizione;
    private final String segno; // + or -
    
    private static final String FILE = "./causali.dat";
    private static final Map<String, Causale> causali = new HashMap<>();

    public Causale(int codice, String descrizione, String segno) {
        this.descrizione = descrizione;
        this.segno = segno;
        this.codice = codice;
        causali.put(descrizione, this);
    }

    public int getCodice() {
        return codice;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public String getSegno() {
        return segno;
    }
    
    public static String getFILE() {
        return FILE;
    }

    public static Map<String, Causale> getCausali() {
        return causali;
    }
    
    public void putMovimento(Causale c) {
        causali.put(c.getDescrizione(), c);
    }
    
    public void removeMovimento(String c) {
        causali.remove(c);
    }

    @Override
    public String toString() {
        return "Causale{" + "codice=" + codice + ", descrizione=" + descrizione + ", segno=" + segno + '}';
    }
    
    private static Map<String, Causale> loadCausali(final File f) {
        try {
            if (!f.exists()) {
                f.createNewFile();
                return new HashMap<>();
            }
            
            if (!f.canRead()) {
                return new HashMap<>();
            }
            
            final ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(f));
            final Map<String, Causale> causale = (Map<String, Causale>) inputStream.readObject();
            
            return causale;

        } catch (final IOException | ClassNotFoundException ex) {
        }

        return new HashMap<>();
    }
    
    public static void saveCausali(final Map<String, Causale> causale, final File f) {
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            
            if (!f.canWrite()) {
                return;
            }
            
            final ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(f));
            outputStream.writeObject(causale);
        } catch (final IOException ex) {
        }
    }
}
