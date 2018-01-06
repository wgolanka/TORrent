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
        System.out.println("Sending CLIENTS LIST finished");
    }

    public static void askHostToChoseDifferentHost(Socket clientSocket) throws IOException {
        System.out.println(TAG + "askHostToChoseDifferentHost");
        PrintWriter out =
                new PrintWriter(clientSocket.getOutputStream(), true);

        out.println("Please chose different host, last one is not available");
    }

    public static void informThatChosenHostIsOk(Socket clientSocket) throws IOException {
        System.out.println(TAG + "informThatChosenHostIsOk");
        PrintWriter out =
                new PrintWriter(clientSocket.getOutputStream(), true);
        out.println(Menu.HOST);
    }

    public static void askHostToSendFileNames(String askingClient, Socket clientSocket) throws IOException {
        System.out.println(TAG + "askHostToSendFileNames");
        PrintWriter out =
                new PrintWriter(clientSocket.getOutputStream(), true);

        out.println(askingClient + Menu.LIST);
    }

    public static void askHostToSendFile(String askingClient, Socket clientSocket, String fileName) throws IOException {
        System.out.println(TAG + "askHostToSendFile");
        PrintWriter out =
                new PrintWriter(clientSocket.getOutputStream(), true);

        out.println(askingClient + Menu.PULL + "/" + fileName);
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

    public static void letClientKnowItCanExit(Socket clientSocket) throws IOException {
        System.out.println(TAG + "letClientKnowItCanExit");
        PrintWriter out =
                new PrintWriter(clientSocket.getOutputStream(), true);
        out.println(Menu.EXIT);
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
                    //TODO: connectionHandler shouldn't depend on input from previous host,
                    //TODO: so connectionHandler should take care of getting client instance
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static void sendFile(String clientInstance, String nextLine) throws IOException {
        System.out.println(TAG + "sendFIle");
        PrintWriter out =
                new PrintWriter(sockets.get(Integer.valueOf(clientInstance)).getOutputStream(),
                        true);
        out.println(nextLine);
    }
}
