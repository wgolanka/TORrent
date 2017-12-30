package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

public class ServerConnectionHandler implements Runnable {
    private Socket clientSocket;
    private int clientInstance;
    public static ArrayList<String> filesNames = new ArrayList<>();
    private final String TAG = "    ServerConnectionHandler: ";

    public ServerConnectionHandler(Socket clientSocket, int clientInstance) {
        this.clientSocket = clientSocket;
        this.clientInstance = clientInstance;
    }

    @Override
    public void run() {
        BufferedReader in;
        System.out.println(TAG + "run " + clientInstance);

        try {
            System.out.println("    Client connected: " + clientSocket.getRemoteSocketAddress().toString() + " " + clientInstance);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String nextLine;

            while (true) {
                if ((nextLine = in.readLine()) != null) {
                    System.out.println(TAG + nextLine);

                    if (nextLine.contains(Menu.HOST + ".")) {
                        int chosenHost = Integer.valueOf(nextLine.substring(5));
                        System.out.println(TAG + chosenHost + ". " + Server.sockets.get(chosenHost).getRemoteSocketAddress());

                        Server.askHostToSendFileList(Server.sockets.get(chosenHost));

                    } else if (nextLine.contains(Menu.LIST + ".")) {
                        filesNames.add(nextLine.substring(5));
                    }

                    switch (nextLine) {
                        case Menu.HOSTLIST:
                            System.out.println(TAG + "Switch HOSTLIST ");
                            System.out.println(TAG + "Send host list to " + clientInstance + " host");
                            Server.sendHostList(clientSocket);
                            break;
                        case Menu.LIST:
                            System.out.println(TAG + "Switch LIST");
                            if (!filesNames.isEmpty()) {
                                if (Server.sockets.containsKey(clientInstance)) {
                                    System.out.println(TAG + "SendFileList to " + clientInstance + ". " +
                                            Server.sockets.get(clientInstance).getRemoteSocketAddress());
                                    Server.sendFileList(Server.sockets.get(clientInstance));
                                } else {
                                    System.err.println("    askingSocket is null");
                                }
                            } else {
                                System.err.println(TAG + "filesNames list is empty");
                            }

                            break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
