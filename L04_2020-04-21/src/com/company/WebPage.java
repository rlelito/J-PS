package com.company;

import jdk.internal.util.xml.impl.Input;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

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

    public void GetLinksFromWebPage(String date) throws MalformedURLException {
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
        for (Element e : linki) {
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

    public void LogingToWebSite() throws UnsupportedEncodingException {
        String address = "http://ux.up.krakow.pl/~pmazurek/java/log-in.php";
        HttpURLConnection httpConn = null;

        HashMap<String, String> tempData = new HashMap<>();
        tempData.put("login", "128666");
        tempData.put("password", "student");

        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> param : tempData.entrySet()) {
            if (sb.length() != 0) sb.append("&");
            sb.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            sb.append("=");
            sb.append(URLEncoder.encode(param.getValue(), "UTF-8"));
        }
        byte[] sendData = sb.toString().getBytes(StandardCharsets.UTF_8);


        try {
            URL url = new URL(address);
            httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestMethod("POST");
            httpConn.setDoOutput(true);
            httpConn.setRequestProperty("Accept", "Mozilla");
            httpConn.setRequestProperty("Content-Length", String.valueOf(sendData.length));
            httpConn.setReadTimeout(10000);
            httpConn.setConnectTimeout(10000);

            httpConn.getOutputStream().write(sendData);

//            String userPass = "128666:student";
//            String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userPass.getBytes()));
//            httpConn.setRequestProperty("Authorization", basicAuth);

            InputStream in = httpConn.getInputStream();
            BufferedReader bufferedR = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

            String line;
            StringBuilder sblink = new StringBuilder();
            while ((line = bufferedR.readLine()) != null) {
                System.out.println(line);
                sblink.append(line);
            }
            httpConn.disconnect();

            Document doc = Jsoup.parseBodyFragment(sblink.toString());
            Elements links = doc.select("a");
//            for (Element e : links) {
//                Pattern filePattern = Pattern.compile("*+\\.+\\(xls\\|csv\\)");
//                if (filePattern.matcher(e.attr("href")).find()) {
//                    System.out.println(e.attr("href"));
//                }
//            }

            FileDownload("http://ux.up.krakow.pl/~pmazurek/java/"+links.get(3).attr("href"),
                    "/Users/rl/Developers/Projects_IntelliJ/L04_2020-04-21/");

        } catch (IOException e) {
            e.printStackTrace();
            httpConn.disconnect();
        }
    }

    public void Cookies(String address) {
        try {
            URL url = new URL(address);
            URLConnection conn = url.openConnection();
            conn.setRequestProperty("Accept", "appication/json");
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(10000);
            String cookies = conn.getHeaderField("Set-Cookie");
            //conn.setRequestProperty("Set-Cookie",);

            System.out.println(cookies);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void FileDownload(String urlFile, String saveFile) {
        try {
            URL url = new URL(urlFile);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                String filename = "";
                String disposition = httpConn.getHeaderField("Content-Disposition");
                System.out.println(disposition);
                String content = httpConn.getContentType();
                int contentLength = httpConn.getContentLength();

                if (disposition != null) {
                    int index = disposition.indexOf("filename=");
                    if (index > 0) {
                        filename = disposition.substring(index + 10, urlFile.length() - 1);
                    }
                } else {
                    filename = urlFile.substring(urlFile.lastIndexOf("/"), urlFile.length());
                }

                InputStream in = httpConn.getInputStream();
                String saveFilePath = saveFile + File.separator + filename;

                FileOutputStream fileOut = new FileOutputStream(saveFilePath);

                int byteRead = -1;
                byte[] buffer = new byte[1024];

                while ((byteRead = in.read(buffer)) != -1) {
                    fileOut.write(buffer, 0, byteRead);
                }
                fileOut.close();
                in.close();

            } else {
                System.out.println("Plik nie istnieje");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
