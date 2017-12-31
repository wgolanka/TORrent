package com.company;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Client {
    Socket clientSocket;

    private ArrayList<String> fileNames = new ArrayList<>();

    private static final String TAG = "    Client: ";
    private String hostName;
    static String filesPath;
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

        final File folder = new File(filesPath);
        for (final File fileEntry : folder.listFiles()) {
            if (!fileEntry.isDirectory()) {
                toServer.println(askingClient + Menu.LIST + "." + fileEntry.getName() +
                        " MD5: " + addMD5(fileEntry));
            }
        }

        toServer.println(askingClient + Menu.FINISHED);
    }

    private StringBuffer addMD5(File fileEntry) {

        StringBuffer stringBuffer = new StringBuffer();
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");

            InputStream is = Files.newInputStream(fileEntry.toPath());
            DigestInputStream dis = new DigestInputStream(is, md);

            while (dis.read() != -1) ;

            byte[] digest = md.digest();
            for (byte b : digest) {
                stringBuffer.append(String.format("%02x", b));
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return stringBuffer;
    }

    public void startAgain() { // TODO it is probably launched on thread and shouldn't be.
        Main.welcomeChoice(this);
    }
}
