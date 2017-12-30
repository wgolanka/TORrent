package com.company;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Server {

    static HashMap<Integer, Socket> sockets;


    public static void sendHostList(Socket clientSocket) throws IOException {
        PrintWriter out =
                new PrintWriter(clientSocket.getOutputStream(), true);

        for (Map.Entry<Integer, Socket> entry : sockets.entrySet()) {
            out.println(entry.getKey() + ". " + entry.getValue().getRemoteSocketAddress());
        }
        out.println(Menu.HOSTLIST);
    }

    public static void askHostToSendFileList(Socket clientSocket) throws IOException {
        System.out.println("    Server: askHostToSendFileList");
        PrintWriter out =
                new PrintWriter(clientSocket.getOutputStream(), true);

        out.println(Menu.LIST);
    }

    public static void sendFileList(Socket clientSocket) throws IOException {
        PrintWriter out =
                new PrintWriter(clientSocket.getOutputStream(), true);

        for (String fileName : ServerConnectionHandler.filesNames) {
            out.println(fileName);
        }

        ServerConnectionHandler.filesNames.clear();
        out.println(Menu.FINISHED);

    }

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(10000);
        sockets = new HashMap<>();
        BufferedReader fromClient;
        String input;

        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();

                fromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                if ((input = fromClient.readLine()) != null) {
                    System.out.println("    connecting to " + clientSocket.getRemoteSocketAddress().toString() + " : " + input);

                    sockets.put(Integer.valueOf(input), clientSocket);

                    Runnable connectionHandler = new ServerConnectionHandler(clientSocket, Integer.valueOf(input));
                    new Thread(connectionHandler).start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
