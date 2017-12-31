package com.company;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Client {
    Socket clientSocket;

    private ArrayList<String> fileNames = new ArrayList<>();

    private static final String TAG = "    Client: ";
    private String hostName;
    static String instanceNumber;

    private int portNumber;

    public Client(String hostName, int portNumber) {
        this.hostName = hostName;
        this.portNumber = portNumber;
    }

    public Socket openConnectionWithServer() {
        try {
            if (clientSocket == null) {
                System.out.println(TAG + "openConnectionWithServer");
                clientSocket = new Socket(hostName, portNumber);
                sendServerMyInstanceNumber();
            }

        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
        } catch (IOException e) {
            System.err.println("Main Client: Couldn't get I/O for the connection to " +
                    hostName);
        }
        return clientSocket;
    }

    private void sendServerMyInstanceNumber() throws IOException {
        System.out.println(TAG + "sendServerMyInstanceNumber");
        PrintWriter toServer =
                new PrintWriter(clientSocket.getOutputStream(), true);

        toServer.println(instanceNumber);
    }

    void getFilesList() {
        System.out.println(TAG + "getFileList");
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

    public void tryAskServerAboutHosts() {
        System.out.println(TAG + "tryAskServerAboutHosts");

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

    private void askServerAboutHosts() throws IOException { // TU OK
        System.out.println(TAG + "askServerAboutHosts");
        PrintWriter toServer =
                new PrintWriter(clientSocket.getOutputStream(), true);

        toServer.println(instanceNumber + Menu.CLIENTS);
    }

    public void sendChosenHostNum(String askingClient, int userInput) throws IOException {
        System.out.println(TAG + "sendChosenHostNum");
        PrintWriter toServer =
                new PrintWriter(clientSocket.getOutputStream(), true);

        toServer.println(askingClient + Menu.HOST + userInput);
    }

    public void sendFileListToServer(String askingClient) throws IOException {
        System.out.println(TAG + "sendFileListToServer");
        PrintWriter toServer =
                new PrintWriter(clientSocket.getOutputStream(), true);

        for (String name : fileNames) {
            toServer.println(askingClient + Menu.LIST + "." + name);
        }

        toServer.println(askingClient + Menu.FINISHED);
    }

    public void startAgain() { // TODO it is probably launched on thread and shouldn't be.
        Main.welcomeChoice(this);
    }
}
