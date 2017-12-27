package com.company;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.HashMap;

public class ServerMain {

    static ArrayList<Socket> sockets;


    public static void sendHostList(Socket clientSocket) throws IOException {
        int counter = 1;
        for (Socket socket : sockets) {

            PrintWriter out =
                    new PrintWriter(clientSocket.getOutputStream(), true);

            out.println(counter++ + ". " + socket.getRemoteSocketAddress());
        }
    }

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(10000);
        sockets = new ArrayList<>();

        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                sockets.add(clientSocket);

                Runnable connectionHandler = new ServerConnectionHandler(clientSocket);
                new Thread(connectionHandler).start();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
