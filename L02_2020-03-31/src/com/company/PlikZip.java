package com.company;

import java.io.*;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class PlikZip {

    public PlikZip() {
    }

    public void ZapisDoArchiwum(File [] pliki, String lokalizacjaDocelowa, String nazwa) {
        try {
            if(pliki.length > 0) {
                Path lokalizacjaZip = pliki[0].toPath().getParent().resolve(lokalizacjaDocelowa + "/" + nazwa);
                ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(lokalizacjaZip.toString()));
                zipOut.setLevel(0);

                for (File f : pliki) {
                    ZipEntry zipE = new ZipEntry(f.getName());
                    FileInputStream fileInputStream = new FileInputStream(f);
                    zipOut.putNextEntry(zipE);
                    System.out.println("Zapisuje do pliku: " + f.getName());

                    byte[] bufor = new byte[1024];
                    int dlugosc;

                    while((dlugosc = fileInputStream.read(bufor)) >= 0) {
                        zipOut.write(bufor, 0, dlugosc);
                    }
                    fileInputStream.close();
                    zipOut.closeEntry();
                }

                zipOut.close();
            } else {
                System.out.println("Brak plikow do zapisania");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
