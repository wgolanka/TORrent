package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientConnectionHandler implements Runnable {

    private Socket clientSocket;

    public ClientConnectionHandler(Socket socket) {
        clientSocket = socket;
    }


    @Override
    public void run() {
        BufferedReader in;
        System.out.println("Client connectionHandler: run");

        try {
            System.out.println("Client being handled: " + clientSocket.getRemoteSocketAddress().toString());
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String nextLine;

            while ((nextLine = in.readLine()) != null) {
                System.out.println("ClientConnectionHandler: " + nextLine);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
