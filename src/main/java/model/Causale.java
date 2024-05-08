/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;

/**
 *
 * @author seba2
 */
public class Causale implements Serializable {
    
    private int lastCodice = 0;
    private final int codice;
    
    private final String descrizione;
    private final char segno; // + or -

    public Causale(String descrizione, char segno) {
        codice = ++lastCodice;
        this.descrizione = descrizione;
        this.segno = segno;
    }

    public int getLastCodice() {
        return lastCodice;
    }

    public int getCodice() {
        return codice;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public char getSegno() {
        return segno;
    }

    @Override
    public String toString() {
        return "Causale{" + "lastCodice=" + lastCodice + ", codice=" + codice + ", descrizione=" + descrizione + ", segno=" + segno + '}';
    }
}
