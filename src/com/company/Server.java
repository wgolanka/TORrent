package com.company;

import java.io.BufferedReader;
import java.io.IOException;

import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.HashMap;

public class Server {

    static ArrayList<String> filesList;
    public static HashMap<Integer, SocketAddress> connectedHosts = new HashMap<>();
    int portNumber = 0;
    static int counter = 0;


    public Server(int port) {
        portNumber = port;
    }

    static ArrayList<String> getFilesList(String filename) {
        if (filename != null) {
            filesList.add(filename);
        }

        return filesList;
    }

    void checkClientCommand(String command, ServerSocket serverSocket) {

        if (command.contains(Menu.HOSTLIST)) {
            showConnectedHosts();
        } else if (command.contains(Menu.LIST)) {
            System.out.println("command contains LIST");
            command = command.replace(Menu.LIST + " ", "");
            showHostFiles(Integer.valueOf(command), serverSocket);
        }

    }

    void showHostFiles(int fileNamesListSize, ServerSocket serverSocket) {
        try {
            while (filesList.size() != fileNamesListSize) {
                Socket connectionSocket = serverSocket.accept();

                BufferedReader inFromClient =
                        new BufferedReader(
                                new InputStreamReader(
                                        connectionSocket.getInputStream())
                        );
                getFilesList(inFromClient.readLine());
            }

            for (String s : filesList) {
                System.out.println("On fileList: " + s);
                System.out.println("...");
            }

        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }

    static void showConnectedHosts() {
        if (!(connectedHosts == null)) {
            System.out.println(connectedHosts.entrySet());
        } else {
            System.out.println("connectedHost map is empty");
        }

    }

    public static void main(String[] args) throws IOException {

        Server server = new Server(10000);
        filesList = new ArrayList<>();

        try (ServerSocket serverSocket =
                     new ServerSocket(server.portNumber)) {

            while (true) {
                Socket connectionSocket = serverSocket.accept();
                System.out.println("Client accepted: " + connectionSocket.getRemoteSocketAddress().toString());
                connectedHosts.put(counter++, connectionSocket.getRemoteSocketAddress());
                BufferedReader inFromClient =
                        new BufferedReader(
                                new InputStreamReader(
                                        connectionSocket.getInputStream())
                        );

//                server.checkClientCommand(inFromClient.readLine(), serverSocket);
                Socket clientSocket = serverSocket.accept();
                Runnable connectionHandler = new ConnectionHandler(clientSocket);
                new Thread(connectionHandler).start();
//                DataOutputStream outToClient =
//                        new DataOutputStream(connectionSocket.getOutputStream());
//                connectionSocket.connect(connectedHosts.get(1));
            }

        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + server.portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}
