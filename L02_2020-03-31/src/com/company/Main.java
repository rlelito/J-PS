package com.company;

import  java.io.*;
import java.nio.file.Path;

public class Main {

    public static void main(String[] args) throws IOException {
	// write your code here

        PlikXML xml = new PlikXML();
//        xml.SaveToXMLFile("Osoba.xml");
//        xml.ReadXMLFile("Osoba.xml");
//        xml.ReadXMLFileXPath("Osoba.xml");
        xml.ReadXMLFileXPathExchange("ExchangeRatesTable.xml");
    }
}
