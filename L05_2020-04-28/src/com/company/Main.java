package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        AppFTPClient client = new AppFTPClient("ux.up.krakow.pl");

        client.ConnectToFTP();
//        client.FindAllFiles("public_html");
//        client.DownloadFile("java/pliki/pogoda.xml", "/Users/rl/Developers");
        client.UploadFile("/Users/rl/Developers", "java//plik/temp/");
        client.DisconnectFTP();
    }
}
