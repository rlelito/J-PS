package com.company;

import java.io.Serializable;

public class Adres implements Serializable{

    String ulicz;
    int numerBudynku;
    int numerLok;
    String maisto;
    String kodPocztowy;

    public Adres(String ulicaN, int budynekNr, int lokal, String miastoN, String poczta) {
        this.ulicz = ulicaN;
        this.numerBudynku = budynekNr;
        this.numerLok = lokal;
        this.maisto = miastoN;
        this.kodPocztowy = poczta;
    }

    @Override
    public String toString() {
        return "Adres w pamięci: " + super.toString() + "\n" + "Ulica: " + this.ulicz +
                "Nr/lok: " + this.numerBudynku + " / " + this.numerLok + "\n" +
                "Miasto: " + this.maisto + " " + this.kodPocztowy;
    }
}
