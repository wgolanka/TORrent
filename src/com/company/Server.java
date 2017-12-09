package com.company;

import java.io.BufferedReader;
import java.io.IOException;

import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    static ArrayList<String> filesList;

    static ArrayList<String> getFilesList(String filename) {
        if (filename != null) {
            filesList.add(filename);
        }

        return filesList;
    }

    public static void main(String[] args) throws IOException {

        if (args.length != 1) {
            System.err.println("Usage: java Server <port number>");
            System.exit(1);
        }

        filesList = new ArrayList<>();

        int portNumber = Integer.parseInt(args[0]);

        String filename;

        try (ServerSocket serverSocket =
                     new ServerSocket(portNumber)) {

            while (true) {
                Socket connectionSocket = serverSocket.accept();

                BufferedReader inFromClient =
                        new BufferedReader(
                                new InputStreamReader(
                                        connectionSocket.getInputStream())
                        );
                getFilesList(inFromClient.readLine());

                for (String s : filesList) {
                    System.out.println("On fileList: " + s);
                    System.out.println("...");
                }
                System.out.println();
            }


        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}
