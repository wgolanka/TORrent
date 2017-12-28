package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientConnectionHandler implements Runnable {

    private Socket clientSocket;
    private Client client;

    public ClientConnectionHandler(Socket socket, Client client) {
        clientSocket = socket;
        this.client = client;
    }


    @Override
    public void run() {
        BufferedReader in;
        System.out.println("    ClientConnectionHandler: run");

        try {
            System.out.println("    Client being handled: " + clientSocket.getRemoteSocketAddress().toString());
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String nextLine;

            while ((nextLine = in.readLine()) != null) {
                System.out.println("    ClientConnectionHandler: nextLine ");

                if (!nextLine.equals(Menu.HOSTLIST)) {
                    System.out.println(nextLine);
                }

                switch (nextLine) {
                    case Menu.HOSTLIST:
                        System.out.println("    ClientConnectionHandler: switch HOSTLIST");
                        client.sendChosenHostNum(Menu.getUserInput());
                        break;
                    case Menu.LIST:
                        System.out.println("    ClientConnectionHandler: switch LIST");
                        client.sendFileListToServer();
                        break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
