package com.company;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class Pliki {

    public Pliki() {
    }

    public Path StartLokalizaji() {
        File f = new File("temp.txt");
        System.out.println(f.getAbsolutePath());
        Path sciezka = Paths.get(f.getAbsolutePath());//.getParent();

        return sciezka.getParent();
    }


    public File[] PobierzListePlikow() {
        Path temp = StartLokalizaji();
//        System.out.println(temp.resolve("/Users/rl/Developers/Projects_IntelliJ/katalog"));

        File[] listaPlikow = temp.toFile().listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                if(name.endsWith(".txt")){
                    return true;
                }
                return false;
            }
        });
        for(File d : listaPlikow) {
            if(d.isFile()) {
//            if(d.getName().endsWith()) {
                System.out.println(d.getName());
            }
        }

        return listaPlikow;
    }

    public void PrzeniesPliki(Path lokalizacja, String gdzie, File[] pliki) {
        Path lokalizacjaDocelowa = lokalizacja.resolve(gdzie);
        if(pliki.length > 0) {
            for(File f: pliki) {
                try {
                    Files.copy(lokalizacja.resolve(f.getName()), lokalizacjaDocelowa.resolve(f.getName()), StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("Przesniesiono plik: " + f.getName());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } else {
            System.out.println("Brak plików do przeslania");
        }
    }

    public void WyswietlInformacjeOPliku(File plik) {
        System.out.println("Nazwa pliku: " + plik.getName());
        System.out.println("Root: " + plik.toPath().getRoot());
        System.out.println("Lokalizacja cakowita: " + plik.getAbsolutePath());
        System.out.println("Wielkosc (B): " + plik.length());
        System.out.println("Czy do odczytu?: " + plik.canRead());
    }

}
