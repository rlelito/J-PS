package com.company;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class PlikXML {

    public PlikXML() {}

    public void SaveToXMLFile(String name) {

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            Element root = doc.createElement("root");
            doc.appendChild(root);

            Element personE = doc.createElement("Osoba");
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

    }
}
