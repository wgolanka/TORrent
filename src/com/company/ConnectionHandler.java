package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ConnectionHandler implements Runnable {
    private Socket clientSocket;

    public ConnectionHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        BufferedReader in;
        System.out.println("ConnectionHandler: run");

        try {
            System.out.println("Client connected: " + clientSocket.getRemoteSocketAddress().toString());
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String nextLine;
            System.out.println("connectionHandler before");
            while (true) {
                if ((nextLine = in.readLine()) != null) {
                    System.out.println(nextLine);
                }

            }

//            System.out.println("connectionhandler after");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
