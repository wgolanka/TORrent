package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {

    static HashMap<Integer, Socket> sockets;


    public static void sendHostList(String askingClientInstance, Socket clientSocket) throws IOException { // TO JEST OK
        PrintWriter out =
                new PrintWriter(clientSocket.getOutputStream(), true);

        for (Map.Entry<Integer, Socket> entry : sockets.entrySet()) {
            out.println(entry.getKey() + ". " + entry.getValue().getRemoteSocketAddress());
        }
        out.println(askingClientInstance + Menu.CLIENTS);
    }

    public static void askHostToSendFileList(String askingClient, Socket clientSocket) throws IOException { // TU OK
        System.out.println("    Server: askHostToSendFileList");
        PrintWriter out =
                new PrintWriter(clientSocket.getOutputStream(), true);

        out.println(askingClient + Menu.LIST);
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

                    Runnable connectionHandler = new ServerConnectionHandler(clientSocket, input);
                    new Thread(connectionHandler).start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void sendFileName(String fileName, Socket clientSocket) throws IOException {
        PrintWriter out =
                new PrintWriter(clientSocket.getOutputStream(), true);
        out.println(fileName);

//        out.println(Menu.FINISHED); TODO add finishing command
    }
}
