package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerConnectionHandler implements Runnable {

    private Socket clientSocket;

    private String clientInstance;
    private final String TAG = "    ServerConnectionHandler: ";

    public ServerConnectionHandler(Socket clientSocket, String clientInstance) {
        this.clientSocket = clientSocket;
        this.clientInstance = clientInstance;
    }

    private void sendHostList(String fromClient) throws IOException {
        clientInstance = fromClient.substring(0, 1);
        System.out.println(TAG + "Send host list to " + clientInstance + " host");
        Server.sendHostList(clientInstance, clientSocket);
    }

    private void askHostToSendFileNames(String fromClient) throws IOException {

        System.out.println(TAG + "HOST: " + fromClient);
        int chosenHost = Integer.valueOf(fromClient.substring(5));
        clientInstance = fromClient.substring(0, 1);
        System.out.println(TAG + chosenHost + ". " + Server.sockets.get(chosenHost).getRemoteSocketAddress());

        Server.askHostToSendFileNames(clientInstance, Server.sockets.get(chosenHost));
    }

    private void sendFileName(String fromClient) throws IOException {

        clientInstance = fromClient.substring(0, 1);
        int clientNumber = Integer.valueOf(clientInstance);

        if (Server.sockets.containsKey(clientNumber)) {

            System.out.println(TAG + "sendFileList to " + clientInstance + ". " +
                    Server.sockets.get(clientNumber).getRemoteSocketAddress());

            Server.sendFileName(fromClient.substring(6), Server.sockets.get(clientNumber));

        } else {
            System.err.println("    askingSocket is null");
        }
    }

    @Override
    public void run() {
        BufferedReader in;
        System.out.println(TAG + "run " + clientInstance);

        try {
            System.out.println("    Client connected: " +
                    clientSocket.getRemoteSocketAddress() + " " + clientInstance);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String nextLine;

            while (true) {
                if ((nextLine = in.readLine()) != null) {
                    System.out.println();
                    System.out.println(TAG + nextLine);

                    if (nextLine.contains(Menu.CLIENTS)) {
                        sendHostList(nextLine);
                    } else if (nextLine.contains(Menu.HOST)) {
                        askHostToSendFileNames(nextLine);
                    } else if (nextLine.contains(Menu.LIST + ".")) {
                        sendFileName(nextLine);
                    } else if (nextLine.contains(Menu.FINISHED)) {
                        Server.sendFinishCommand(Server.sockets.get(
                                Integer.valueOf(nextLine.substring(0, 1))));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
