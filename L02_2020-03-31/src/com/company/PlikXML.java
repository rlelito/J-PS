package com.company;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class PlikXML {

    public PlikXML() {
    }

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

            Transformer t = TransformerFactory.newInstance().newTransformer();
            t.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            t.setOutputProperty(OutputKeys.METHOD, "XML");
            t.setOutputProperty(OutputKeys.INDENT, "yes");

            t.transform(new DOMSource(doc), new StreamResult(new FileOutputStream(name)));

        } catch (ParserConfigurationException | FileNotFoundException | TransformerException e) {
            e.printStackTrace();
        }
    }

    public void ReadXMLFile(String name) {
        try {
            FileInputStream f = new FileInputStream(name);
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

            Document doc = builder.parse(f);

            Element root = doc.getDocumentElement();
            //System.out.println(root.toString());

//            NodeList temp = root.getElementsByTagName("Osoba");
//            System.out.println("len" + temp.getLength());
//            Element tempE = (Element) temp.item(0);
//            System.out.println("tag:" + tempE.getTagName());
//            System.out.println("attr:" + tempE.getAttribute("nr"));

            NodeList temp = root.getElementsByTagName("Osoba");
            System.out.println("len:" + temp.getLength());
            if (temp.item(0).getChildNodes().getLength() > 0) {
                NodeList tempE = temp.item(0).getChildNodes();
                System.out.println("node len:" + tempE.getLength());

                for (int i = 0; i < tempE.getLength(); i++) {
                    if (tempE.item(i) instanceof Element) {
//                        Node node = tempE.item(i);
//                        System.out.println(node.getNodeName());
//                        System.out.println(node.getTextContent());
                        Element element = (Element) tempE.item(i);
                        System.out.println(element.getTextContent());

                    }
                }
            }

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    public void ReadXMLFileXPath(String name) {
        try {
            FileInputStream f = new FileInputStream(name);
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

            Document doc = builder.parse(f);

            XPathFactory factory = XPathFactory.newInstance();
            XPath xPath = factory.newXPath();

            XPathExpression exp = xPath.compile("//Osoba");
            NodeList listPerson = (NodeList) exp.evaluate(doc, XPathConstants.NODESET);
            System.out.println(listPerson.getLength());

            XPathExpression exp2 = xPath.compile("//Osoba/Imie");
            String person = (String) exp2.evaluate(doc, XPathConstants.STRING);
            System.out.println(person);

        } catch (IOException | ParserConfigurationException | SAXException | XPathExpressionException e) {
            e.printStackTrace();
        }
    }

    public void ReadXMLFileXPathExchange(String name) {
        // http://api.nbp.pl/api/exchangerates/tables/A/
        try {
            FileInputStream f = new FileInputStream(name);
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

            Document doc = builder.parse(f);

            XPathFactory factory = XPathFactory.newInstance();
            XPath xPath = factory.newXPath();

            XPathExpression exp = xPath.compile("//Rates");
            NodeList listPerson = (NodeList) exp.evaluate(doc, XPathConstants.NODESET);
            System.out.println(listPerson.getLength());

//            XPathExpression exp2 = xPath.compile("//Rate[Mid>1.5 and Mid < 3.5]");
//            XPathExpression exp2 = xPath.compile("//Rate[contains(Code, 'L')]/Currency");
            XPathExpression exp2 = xPath.compile("//Rate[last()]/Currency");
            NodeList codeList = (NodeList) exp2.evaluate(doc, XPathConstants.NODESET);
            for (int z = 0; z < codeList.getLength(); z++) {
//                System.out.println(codeList.item(z).getTextContent());
                Element rateE = (Element)codeList.item(z);
                System.out.println(rateE.getTextContent());
            }

        } catch (IOException | ParserConfigurationException | SAXException | XPathExpressionException e) {
            e.printStackTrace();
        }
    }

}
