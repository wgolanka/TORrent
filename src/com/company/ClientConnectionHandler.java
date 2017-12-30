package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientConnectionHandler implements Runnable {

    private Socket clientSocket;
    private Client client;
    private final String TAG = "    ClientConnectionHandler: ";

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

            while ((nextLine = in.readLine()) != null) {
                System.out.println(TAG + "nextLine ");

                if (!nextLine.equals(Menu.HOSTLIST)) {
                    System.out.println(nextLine);
                }

                switch (nextLine) {
                    case Menu.HOSTLIST:
                        System.out.println(TAG + "switch HOSTLIST");
                        client.sendChosenHostNum(Menu.getUserInput());
                        break;
                    case Menu.LIST:
                        System.out.println(TAG + "Server ask to send my file list");
                        System.out.println(TAG + "switch LIST");
                        client.sendFileListToServer();
                        break;
                    case Menu.FINISHED:
                        Main.welcomeChoice(client);
                        break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
