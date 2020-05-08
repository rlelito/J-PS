package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

public class WebPage {
    public WebPage() {

    }

    public String GetDataFromWebPage(String address) {
        try {
            URL url = new URL(address);
            URLConnection conn = url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(10000);
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("User-Agent", "Mozilla");

            BufferedReader buffered = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = buffered.readLine()) != null) {
                System.out.println(line);
                sb.append(line);
            }
            buffered.close();
            return sb.toString();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void SendDataToWebPage(String date, String address) {
        try {
            URL url = new URL(address);
            URLConnection conn = url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(10000);
            conn.setDoOutput(true);
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("User-Agent", "Mozilla");

            BufferedWriter bufferedW = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), StandardCharsets.UTF_8));
            bufferedW.write(date);
            bufferedW.flush();
            bufferedW.close();

            BufferedReader bufferedR = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));

            //StringBuilder sb = new StringBuilder();
            String line;

            while ((line = bufferedR.readLine()) != null) {
                System.out.println(line);
            }
            bufferedR.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void GetLinksFromWebPage(String date) {
        Document doc = Jsoup.parse(date);
//        System.out.println(doc.head());

        Element body = doc.body();
        Elements linki = body.select("a[href]");

        //        Elements linki = body.select("img");
//        System.out.println(linki.size());

//        for (Element e : linki) {
//            System.out.println(e.attr("href"));
//        }

        System.out.println(linki.get(10).attr("abs:href"));
        String temp = GetDataFromWebPage(linki.get(10).attr("abs:href"));
        System.out.println(temp);
        doc = Jsoup.parse(temp);
        body = doc.body();

        linki = body.select("a[href]");
        System.out.println(linki.size());
        for (Element e: linki) {
            URL tempLink = new URL(e.attr("abs:href"));
            if (!tempLink.equals("www.up.krakow.pl")) {
                System.out.println(tempLink.getHost());
            }
        }
    }

    public static void GetConnectionInfo(URLConnection connection, URL url) {
        System.out.println("Protokol:" + url.getProtocol());
        System.out.println("Info: " + url.getUserInfo());
        String host = url.getHost();
        if (host != null) {
            int atSign = host.indexOf('@');
        }
    }
}
