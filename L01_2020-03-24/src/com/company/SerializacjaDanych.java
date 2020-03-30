package com.company;

import java.io.*;

public class SerializacjaDanych {

    public SerializacjaDanych() {
    }

    public void ZapisDanychDoPliku() {
        Adres adres = new Adres("Kwiatowa", 12, 3,"Krakow", "32-100");
        Osoba osoba1 = new Osoba("Jan", "Kowalski", adres);
        Osoba osoba2 = new Osoba("Ewa", "Kowalska", adres);

        try {
            ObjectOutputStream objOsoba = new ObjectOutputStream(new FileOutputStream("Osoby.dat"));
            objOsoba.writeObject(osoba1);
            objOsoba.writeObject(osoba2);
            objOsoba.close();
            System.out.println("Zapisano dane");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void OdczytDanychZPliku() {
        try {
            ObjectInputStream objIn = new ObjectInputStream(new FileInputStream("Osoby.dat"));
            Osoba osoba3 = (Osoba)objIn.readObject();
            Osoba osoba4 = (Osoba)objIn.readObject();

            System.out.println(osoba3.toString());
            System.out.println(osoba4.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
