package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientConnectionHandler implements Runnable {

    private Socket clientSocket;
    private Client client;
    private final String TAG = "    ClientConnectionHandler: ";
    private String askingClient;

    public ClientConnectionHandler(Socket socket, Client client) {
        clientSocket = socket;
        this.client = client;
    }


    @Override
    public void run() {
        BufferedReader in;
        System.out.println(TAG + "run");

        try {
            System.out.println(TAG + clientSocket.getRemoteSocketAddress().toString());
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String nextLine;

            while (true) {
                if ((nextLine = in.readLine()) != null) {
                    System.out.println(TAG + "nextLine ");

                    if (nextLine.contains(Menu.CLIENTS)) { // TO JEST OK
                        System.out.println(TAG + "Server asked to chose host");
                        int userInput = Menu.getUserInput();
                        askingClient = nextLine.substring(0, 1);
                        client.sendChosenHostNum(askingClient, userInput);
                    } else if (nextLine.contains(Menu.LIST)) { // TU OK
                        System.out.println(TAG + "Server asked to send my file list");
                        askingClient = nextLine.substring(0, 1);
                        client.sendFileListToServer(askingClient);
                    } else {
                        System.out.println(nextLine);
                    }

                    switch (nextLine) {
                        case Menu.FINISHED:
                            Main.welcomeChoice(client);
                            break;
                    }
                } else {
                    System.out.println("GOWNO DUPA");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
