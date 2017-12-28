package com.company;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Client {

    public ArrayList<String> fileNames = new ArrayList<>();
    private String hostName;
    private int portNumber;
    static int instanceNumber;
    Socket clientSocket;


    public Client(String hostName, int portNumber) {
        this.hostName = hostName;
        this.portNumber = portNumber;
    }

    public Socket openConnectionWithServer() {
        try {
            if (clientSocket == null) {
                System.out.println("    Client: openConnectionWithServer");
                clientSocket = new Socket(hostName, portNumber);
            }

        } catch (UnknownHostException e) {
            System.err.println("D   on't know about host " + hostName);
        } catch (IOException e) {
            System.err.println("    Main Client: Couldn't get I/O for the connection to " +
                    hostName);
        }

        return clientSocket;
    }

    void getFilesList() {

        String folderPath = "/Users/wgolanka/Documents/School/#3 semester/SKJ/Tor/TORrent_" + instanceNumber;
        File folder = new File(folderPath);
        File[] listOfFiles = folder.listFiles();

        if (listOfFiles == null)
            return;

        for (int i = 0; i < listOfFiles.length; i++) {
            String name = listOfFiles[i].getName();
            fileNames.add(name);
        }
    }

    public void sendChosenHostNum(int userInput) throws IOException {
        System.out.println("    Client: sendChosenHostNum");
        PrintWriter toServer =
                new PrintWriter(clientSocket.getOutputStream(), true);

        toServer.println(Menu.HOST + "." + String.valueOf(userInput));
    }

    public void sendFileListToServer() throws IOException {
        System.out.println("    Client: sendFileListToServer");

        PrintWriter toServer =
                new PrintWriter(clientSocket.getOutputStream(), true);

        for (String name : fileNames) {
            toServer.println(Menu.LIST + "." + name);
        }

        toServer.println(Menu.LIST);
    }

    public void tryAskServerAboutHosts() {
        System.out.println("    Client: tryAskServerAboutHosts");
        try {
            askServerAboutHosts();
        } catch (UnknownHostException e) {
            System.err.println("    Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("    Client: Couldn't get I/O for the connection to " +
                    hostName);
            System.exit(1);
        }
    }

    private void askServerAboutHosts() throws IOException {

        PrintWriter toServer =
                new PrintWriter(clientSocket.getOutputStream(), true);

        toServer.println(Menu.HOSTLIST);
    }


}
