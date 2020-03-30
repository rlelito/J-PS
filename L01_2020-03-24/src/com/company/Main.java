package com.company;

import  java.io.*;
import java.nio.file.Path;

public class Main {

    public static void main(String[] args) throws IOException {
	// write your code here
        SerializacjaDanych sd = new SerializacjaDanych();
//        sd.ZapisDanychDoPliku();
//        sd.OdczytDanychZPliku();

        Pliki p = new Pliki();
//        p.StartLokalizaji();

        Path lokalizacja = p.StartLokalizaji();
        File[] temp = p.PobierzListePlikow();
//        p.PrzeniesPliki(lokalizacja, "katalog", temp);

        p.WyswietlInformacjeOPliku(temp[0]);

        PlikZip pZip = new PlikZip();
        pZip.ZapisDoArchiwum(temp, "katalog", "archiwumZip.zip");
    }
}
