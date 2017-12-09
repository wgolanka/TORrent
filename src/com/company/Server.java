package com.company;

import java.io.BufferedReader;
import java.io.IOException;

import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    static ArrayList<String> filesList;
    int clientCounter = 0;
    int portNumber = 0;


    public Server(int port) {
        portNumber = port;
    }

    static ArrayList<String> getFilesList(String filename) {
        if (filename != null) {
            filesList.add(filename);
        }

        return filesList;
    }

    void setClientCount(int count) {
        clientCounter = count;
    }

    int getClientsCount() {
        return clientCounter;
    }

    void checkClientCommand(String command, ServerSocket serverSocket) {

        if (command.contains(Menu.LIST)) {
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


    public static void main(String[] args) throws IOException {

        Server server = new Server(10000);

        filesList = new ArrayList<>();
        int portNumber = server.portNumber;

        try (ServerSocket serverSocket =
                     new ServerSocket(portNumber)) {

            while (true) {
                Socket connectionSocket = serverSocket.accept();


                BufferedReader inFromClient =
                        new BufferedReader(
                                new InputStreamReader(
                                        connectionSocket.getInputStream())
                        );
//                getFilesList(inFromClient.readLine());
                server.checkClientCommand(inFromClient.readLine(), serverSocket);
            }

        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}
