package com.company;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Client {

    String hostName;
    int portNumber;
    ArrayList<String> fileNames;

    public Client(String hostName, int portNumber) {
        this.hostName = hostName;
        this.portNumber = portNumber;
    }

    void getFilesList() {

        if (fileNames == null) {
            fileNames = new ArrayList<>();
        }
        String folderPath = "/Users/wgolanka/Documents/School/#3 semester/SKJ/Tor/TORrent_2";
        File folder = new File(folderPath);
        File[] listOfFiles = folder.listFiles();

        if (listOfFiles == null)
            return;

        for (int i = 0; i < listOfFiles.length; i++) {
            String name = listOfFiles[i].getName();
            fileNames.add(name);
        }

//        for(String s : fileNames){
//            System.out.println(s);
//        }
    }


    public static void main(String[] args) throws IOException {

        if (args.length != 2) {
            System.err.println(
                    "Usage: java Client <host name> <port number>");
            System.exit(1);
        }


        String testFilesSending[] = {"Ala.jpg", "ma.png", "kota.mp3"};

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

        int counter = 0;
        while (counter < 2) {
            try (Socket clientSocket = new Socket(hostName, portNumber)) {


                DataOutputStream outToServer =
                        new DataOutputStream(clientSocket.getOutputStream());

                for (; counter < testFilesSending.length; counter++) {
                    outToServer.writeBytes(testFilesSending[counter]);

                }
//            outToServer.writeBytes(testFilesSending[0]);
//            outToServer.writeBytes(testFilesSending[1]);
//                outToServer.writeBytes(testFilesSending[2]);

            } catch (UnknownHostException e) {
                System.err.println("Don't know about host " + hostName);
                System.exit(1);
            } catch (IOException e) {
                System.err.println("Couldn't get I/O for the connection to " +
                        hostName);
                System.exit(1);
            }
        }

    }
}
