package com.company;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Client {
    Socket clientSocket;

    private static final String TAG = "    Client: ";
    private String hostName;
    String filesPath;
    static String instance;


    private int portNumber;
    private boolean isChosen = false;

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

        toServer.println(instance);
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

        toServer.println(instance + Menu.CLIENTS);
    }

    public void tryAskHostForFileNamesFrom(int chosenHost) {
        System.out.println(TAG + "tryAskHostForFileNamesFrom");

        try {
            askHostForFileNamesFrom(chosenHost);
        } catch (UnknownHostException e) {
            System.err.println("    Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("    Client: Couldn't get I/O for the connection to " +
                    hostName);
            System.exit(1);
        }
    }

    private void askHostForFileNamesFrom(int chosenHost) throws IOException {
        System.out.println(TAG + "askHostForFileNamesFrom");
        PrintWriter toServer =
                new PrintWriter(clientSocket.getOutputStream(), true);

        toServer.println(instance + Menu.FILENAMES + "/" + chosenHost);
    }

    public void setChosenHostState(boolean state) {
        isChosen = state;
    }

    public boolean hostIsChosen() {
        return isChosen;
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
                toServer.println(askingClient + Menu.LIST + "/" + fileEntry.getName() +
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

    public void finishConnection(String askingClient) throws IOException {

        PrintWriter toServer =
                new PrintWriter(clientSocket.getOutputStream(), true);

        toServer.println(askingClient + Menu.EXIT);
        System.out.println(TAG + "send server info about exit");
    }


    public void pullFile(String instance, int chosenHost, String fileName) throws IOException {
//        TODO: continue, cover situation when files isn't available / wrongly written.

        PrintWriter toServer =
                new PrintWriter(clientSocket.getOutputStream(), true);

        toServer.println(instance + Menu.PULL + chosenHost + "/" + fileName);

    }

    public void sendFileToServer(String askingClient, String fileName) throws IOException {

        fileName = filesPath + "/" + fileName;

        File myFile = new File(fileName);
        byte[] fileBytes = new byte[(int) myFile.length()];

        BufferedInputStream bufferedInputStream =
                new BufferedInputStream(new FileInputStream(myFile));

        bufferedInputStream.read(fileBytes, 0, fileBytes.length);

        String command = askingClient + "/" + Menu.PULL;

        byte[] fileAndCommand = joinBoth(command.getBytes(), fileBytes);
        fileBytes = joinBoth(fileAndCommand, fileBytes);


        OutputStream toServer = clientSocket.getOutputStream();

        toServer.write(fileBytes, 0, fileBytes.length);

        System.out.println("Send " + fileName + "(" + fileBytes.length + " bytes)");
    }

    public static byte[] joinBoth(byte[] arr, byte[] arr1) {
        byte[] both = new byte[arr.length + arr1.length];
        int j = 0;
        for (int i = 0; i < both.length; i++) {

            if (i < arr.length) {
                both[i] = arr[i];
            } else {
                both[i] = arr1[j++];
            }
        }
        return both;
    }

}
