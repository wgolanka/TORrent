package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientConnectionHandler implements Runnable {

    private Socket clientSocket;

    private volatile boolean exit = false;
    public static volatile boolean readyToGo = false;

    private Client client;

    private final String TAG = "    ClientConnectionHandler: ";

    public ClientConnectionHandler(Socket socket, Client client) {
        clientSocket = socket;
        this.client = client;
    }

    private void sendFileNamesToServer(String fromServer) throws IOException {
        System.out.println(TAG + "Server asked to send my file list");
        String askingClient = InputResolver.getClientInstance(fromServer);
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

                if (exit) {
                    return;
                }

                if ((nextLine = in.readLine()) != null) {
                    System.out.println(TAG + "nextLine is");

                    if (nextLine.contains(Menu.LIST)) {
                        sendFileNamesToServer(nextLine);
                    } else if (nextLine.contains(Menu.EXIT)) {
                        exit = true;
                    } else if (nextLine.contains(Menu.HOST)) {
                        client.setChosenHostState(true);

                    } else if (nextLine.contains(Menu.FINISHED)) {
                        client.setChosenHostState(false);
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
