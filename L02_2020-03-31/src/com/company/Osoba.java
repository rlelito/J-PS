package com.company;

import java.io.Serializable;

public class Osoba implements Serializable{

    String imie;
    String nazwisko;
    Adres adresOsoby;

    public Osoba(String imieN, String nazwiskoN, Adres adresZameldowania) {
        this.imie = imieN;
        this.nazwisko = nazwiskoN;
        this.adresOsoby = adresZameldowania;
        System.out.println("Dodano nową osobe");
    }

    @Override
    public String toString() {
        return "Adres w pamieci: " + super.toString() + "\n" +
                "Immie: " + this.imie + "Nazwisko: " + this.nazwisko + "\n" + this.adresOsoby.toString();
    }

}
