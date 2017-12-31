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

    private void sendChosenHostNumber(String fromServer) throws IOException {
        System.out.println(TAG + "Server asked to chose host");
        int userInput = Menu.getUserInput();
        askingClient = fromServer.substring(0, 1);
        client.sendChosenHostNum(askingClient, userInput);
    }

    private void sendFileNamesToServer(String fromServer) throws IOException {
        System.out.println(TAG + "Server asked to send my file list");
        askingClient = fromServer.substring(0, 1);
        client.sendFileListToServer(askingClient);
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
                    System.out.print(TAG + "nextLine is");

                    if (nextLine.contains(Menu.CLIENTS)) {
                        sendChosenHostNumber(nextLine);
                    } else if (nextLine.contains(Menu.LIST)) {
                        sendFileNamesToServer(nextLine);
                    } else if (nextLine.contains(Menu.FINISHED)) {
                        Main.welcomeChoice(client);
                    } else {
                        System.out.println(nextLine);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
