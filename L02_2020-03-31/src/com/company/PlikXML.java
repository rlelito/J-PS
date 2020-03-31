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
            personE.setAttribute("nr", "1");
            root.appendChild(personE);

            Element nameE = doc.createElement("Imie");
            nameE.setTextContent("Jan");
            Element lastnameE = doc.createElement("Nazwisko");
            lastnameE.setTextContent("Kowalski");

            personE.appendChild(nameE);
            personE.appendChild(lastnameE);

            Element adressE = doc.createElement("Adres");
            personE.appendChild(adressE);

            Element streetE = doc.createElement("Ulica");
            streetE.setTextContent("Kwiatowa");
            Element buildingE = doc.createElement("Budynek");
            buildingE.setAttribute("nr", "12");
            buildingE.setAttribute("lok_nr", "1");
            Element cityE = doc.createElement("Miasto");
            cityE.setTextContent("Krakow");
            cityE.setAttribute("code", "32-100");
            adressE.appendChild(streetE);
            adressE.appendChild(buildingE);
            adressE.appendChild(cityE);

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

    }
}
