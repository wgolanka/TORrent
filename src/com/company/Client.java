package com.company;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Client {

    String hostName;
    int portNumber;
    int instanceNumber;
    ArrayList<String> fileNames = new ArrayList<>();

    public Client(String hostName, int portNumber) {
        this.hostName = hostName;
        this.portNumber = portNumber;
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

    void sendServerCommandToListFiles() {
        System.out.println("in: sendServerCommandToListFiles");
        try (Socket clientSocket = new Socket(hostName, portNumber)) {

            DataOutputStream outToServer =
                    new DataOutputStream(clientSocket.getOutputStream());

            outToServer.writeBytes(Menu.LIST + " "
                    + String.valueOf(fileNames.size()));

        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                    hostName);
            System.exit(1);
        }
    }


    void sendFileListToServer() {
        System.out.println("in: sendFileListToServer");

        for (String name : fileNames) {
            try (Socket clientSocket = new Socket(hostName, portNumber)) {

                DataOutputStream outToServer =
                        new DataOutputStream(clientSocket.getOutputStream());

                outToServer.writeBytes(name);

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

    public void askServerAboutHosts() {
        System.out.println("in: askServerAboutHosts");
        try (Socket clientSocket = new Socket(hostName, portNumber)) {
            System.out.println("client remoteSocketAddress " + clientSocket.getRemoteSocketAddress().toString());
            Server.connectedHosts.put(instanceNumber, clientSocket.getRemoteSocketAddress());
            DataOutputStream outToServer =
                    new DataOutputStream(clientSocket.getOutputStream());

            outToServer.writeBytes(Menu.HOSTLIST);

        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                    hostName);
            System.exit(1);
        }
    }

    public static void main(String[] args) throws IOException {

        Client client = new Client("Wiktorias-MacBook-Pro.local", 10000);
        client.instanceNumber = Integer.valueOf(args[0]);

        String hostName = client.hostName;
        int portNumber = client.portNumber;
        try (Socket clientSocket = new Socket(hostName, portNumber)) {


            BufferedReader inFromServer =
                    new BufferedReader(new
                            InputStreamReader(clientSocket.getInputStream()));


            DataOutputStream outToServer =
                    new DataOutputStream(clientSocket.getOutputStream());
            outToServer.writeBytes(String.valueOf(client.instanceNumber));


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
