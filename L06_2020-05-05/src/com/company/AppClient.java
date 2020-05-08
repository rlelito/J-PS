package com.company;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class AppClient {
    public AppClient() {
        try {
            Socket socket = new Socket("debian-server", 5050);

            OutputStream out = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(out, true);

            Scanner inputC = new Scanner(System.in);
            String line;

            InputStream input = socket.getInputStream();
            BufferedReader buff = new BufferedReader((new InputStreamReader(input, StandardCharsets.UTF_8)));

            System.out.println(buff.readLine());
            while ( (line = inputC.nextLine()) != null) {
                writer.println(line);
                System.out.println(buff.readLine());
                if(line.equals("exit")) {
                    break;
                }
            }

            System.out.println("Koncze polaczenie z serwerem");
            out.close();
            writer.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
