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
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author seba2
 */
public class Movimento implements Serializable {
    
    private int lastCodice = 0;
    private final int codice;
    
    private final LocalDate date;
    private final Causale causale;
    private final String articoloNome; // getter from Articolo map
    private final int quantità;
    private final double valoreUnitario;
    private final int iva; // %
    
    private static final String FILE = "./movimenti.dat";
    private static final Map<Integer, Movimento> movimenti = loadMovimenti(new File(FILE));

    public Movimento(LocalDate date, Causale causale, String articoloNome, int quantità, double valoreUnitario, int iva) {
        codice = ++lastCodice;
        this.date = date;
        this.causale = causale;
        this.articoloNome = articoloNome;
        this.quantità = quantità;
        this.valoreUnitario = valoreUnitario;
        this.iva = iva;
    }

    public int getLastCodice() {
        return lastCodice;
    }

    public int getCodice() {
        return codice;
    }

    public LocalDate getDate() {
        return date;
    }

    public Causale getCausale() {
        return causale;
    }

    public String getArticoloNome() {
        return articoloNome;
    }

    public int getQuantità() {
        return quantità;
    }

    public double getValoreUnitario() {
        return valoreUnitario;
    }

    public int getIva() {
        return iva;
    }
    
    public static String getFILE() {
        return FILE;
    }

    public static Map<Integer, Movimento> getMovimenti() {
        return movimenti;
    }
    
    public void putMovimento(Movimento m) {
        movimenti.put(m.getCodice(), m);
    }
    
    public void removeMovimento(int c) {
        movimenti.remove(c);
    }
    
//    public Articolo getArticolo() {
//        return Articolo.articoli.get();
//    }

    @Override
    public String toString() {
        return "Movimento{" + "lastCodice=" + lastCodice + ", codice=" + codice + ", date=" + date + ", causale=" + causale + ", articoloNome=" + articoloNome + ", quantit\u00e0=" + quantità + ", valoreUnitario=" + valoreUnitario + ", iva=" + iva + '}';
    }
    
    private static Map<Integer, Movimento> loadMovimenti(final File f) {
        try {
            if (!f.exists()) {
                f.createNewFile();
                return new HashMap<>();
            }
            
            if (!f.canRead()) {
                return new HashMap<>();
            }
            
            final ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(f));
            final Map<Integer, Movimento> movimento = (Map<Integer, Movimento>) inputStream.readObject();
            
            return movimento;

        } catch (final IOException | ClassNotFoundException ex) {
        }

        return new HashMap<>();
    }
    
    public static void saveMovimenti(final Map<Integer, Movimento> movimento, final File f) {
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            
            if (!f.canWrite()) {
                return;
            }
            
            final ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(f));
            outputStream.writeObject(movimento);
        } catch (final IOException ex) {
        }
    }
}
