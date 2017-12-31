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
    private static final String TAG = "    Server: ";


    public static void sendHostList(String askingClientInstance, Socket clientSocket) throws IOException {
        System.out.println(TAG + "sendHostList");

        PrintWriter out =
                new PrintWriter(clientSocket.getOutputStream(), true);

        for (Map.Entry<Integer, Socket> entry : sockets.entrySet()) {
            out.println(entry.getKey() + ". " + entry.getValue().getRemoteSocketAddress());
        }
        out.println(askingClientInstance + Menu.CLIENTS);
    }

    public static void askHostToSendFileNames(String askingClient, Socket clientSocket) throws IOException {
        System.out.println(TAG + "askHostToSendFileNames");
        PrintWriter out =
                new PrintWriter(clientSocket.getOutputStream(), true);

        out.println(askingClient + Menu.LIST);
    }

    public static void sendFileName(String fileName, Socket clientSocket) throws IOException {
        System.out.println(TAG + "SendFileName");
        PrintWriter out =
                new PrintWriter(clientSocket.getOutputStream(), true);
        out.println(fileName);
    }

    public static void sendFinishCommand(Socket clientSocket) throws IOException {
        System.out.println(TAG + "sendFinishCommand");
        PrintWriter out =
                new PrintWriter(clientSocket.getOutputStream(), true);
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
                    System.out.println(TAG + "connecting to " + clientSocket.getRemoteSocketAddress().toString() + " : " + input);

                    sockets.put(Integer.valueOf(input), clientSocket);

                    Runnable connectionHandler = new ServerConnectionHandler(clientSocket, input);
                    new Thread(connectionHandler).start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
