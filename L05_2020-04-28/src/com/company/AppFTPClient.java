package com.company;

import com.jcraft.jsch.*;

import java.io.*;
import java.util.Scanner;
import java.util.Vector;

public class AppFTPClient {
    private JSch jsch;
    private Session session;
    private FTPData ftpData;
    private Channel channel;
    private ChannelSftp channelSftp;
    private String home;
    private String host;

    public AppFTPClient(String nHost) {
        this.host = nHost;
        this.jsch = new JSch();
        // utworzenie obiektu z danymi logowania
        this.ftpData = new FTPData();
    }

    public boolean ConnectToFTP() {
        session = null;
        try {
            session = jsch.getSession(ftpData.GetLogin(), host, 22);
            session.setConfig("StrictHostKEy Checking", "no");
            session.setPassword(ftpData.GetPassword());
            System.out.println("Nawiazuje polazczenie");
            session.connect(10000);
            channel = session.openChannel("sftp");
            channel.connect();
            channelSftp = (ChannelSftp) channel;

            home = channelSftp.getHome();
            System.out.println("Miejsce poczatkowe: " + home);

            return true;
        } catch (JSchException e) {
            e.printStackTrace();
            return false;
        } catch (SftpException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void DisconnectFTP() {
        if (session.isConnected()) {
            channelSftp.exit();
            channel.disconnect();
            session.disconnect();
            System.out.println("Polaczenie zakonczone");
        } else {
            System.out.println("Polaczenie nieistnialo");
        }
    }

    public void FindAllFiles(String location) {
        try {
            String tempLocation;
            if (location.isEmpty()) {
                tempLocation = home;
            } else {
                tempLocation = home + location;
            }

            Vector listAllFiles = channelSftp.ls(home);
            for (int i = 0; i < listAllFiles.size(); i++) {
                ChannelSftp.LsEntry file = (ChannelSftp.LsEntry) listAllFiles.get(i);
                if (!file.getFilename().endsWith(".")) {
                    System.out.println(file.getFilename() + " " + file.getAttrs().getSize());
                }
//                    System.out.println(listAllFiles.get(i).toString());
            }
        } catch (SftpException e) {
            e.printStackTrace();
            System.out.println("podana lokalizacja nie istnieje");
            System.out.println(home + location);
        }
    }

    public  void DownloadFile(String fileLocation, String destLocation) {
        try {
            System.out.println("Pobierany jest plik z lokalizacji: ");
            System.out.println(home + fileLocation);
            channelSftp.get(home + fileLocation, destLocation);
        } catch (SftpException e ) {
            e.printStackTrace();
            System.out.println("problem z pobraniem pliku");
        }
    }

    public void UploadFile(String fileLocation, String destLocation) {
        try {
            String[] tempLocation = destLocation.split("/");
            for (String folder : tempLocation) {
                if (folder.length() > 0 || folder.contains(".")) {
                    try {
                        channelSftp.cd(folder);
                    } catch (SftpException e) {
                        System.out.println("katalog nie istnieje");
                        System.out.println("Czy stworzyc katalog: " + folder);
                        Scanner input = new Scanner(System.in);
                        if ( input.nextLine().equals("T")) {
                            channelSftp.mkdir(folder);
                            channelSftp.cd(folder);
                        } else {
                            System.out.println("Przerwano wysylanie pliku");
                            break;
                        }
//                        e.printStackTrace();
                    }
                }
            }
            channelSftp.put(fileLocation, channelSftp.pwd());
            System.out.println("Plik zostal wyslany na serwer");
        } catch (SftpException e) {
            e.printStackTrace();
        }
    }

    public void RemoveFile(String fileLocation) {
        try {
            Scanner input = new Scanner(System.in);
            if ( channelSftp.ls(home + fileLocation).size() > 0 ) {
                System.out.println("Czy chcesz usunac plik: T/N");
                System.out.println(home + fileLocation);
                if ( input.nextLine().equals("T") ) {
                    channelSftp.rm(home + fileLocation);
                    System.out.println("Usunieto plik");
                }
            }
            System.out.println("");
        } catch (SftpException e) {
            e.printStackTrace();
            System.out.println("Plik nieistnieje");
        }
    }

}
