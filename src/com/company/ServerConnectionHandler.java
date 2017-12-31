package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

public class ServerConnectionHandler implements Runnable {
    private Socket clientSocket;
    private String clientInstance;
    private final String TAG = "    ServerConnectionHandler: ";

    public ServerConnectionHandler(Socket clientSocket, String clientInstance) {
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
                    System.out.println();
                    System.out.println(TAG + nextLine);

                    if (nextLine.contains(Menu.CLIENTS)) { // TO JEST OK
                        clientInstance = nextLine.substring(0, 1);
                        System.out.println(TAG + "Send host list to " + clientInstance + " host");
                        Server.sendHostList(clientInstance, clientSocket);
                    }

                    if (nextLine.contains(Menu.HOST)) { // TU OK
                        System.out.println(TAG + "HOST: " + nextLine);
                        int chosenHost = Integer.valueOf(nextLine.substring(5));
                        clientInstance = nextLine.substring(0, 1);
                        System.out.println(TAG + chosenHost + ". " + Server.sockets.get(chosenHost).getRemoteSocketAddress());

                        Server.askHostToSendFileList(clientInstance, Server.sockets.get(chosenHost));

                    } else if (nextLine.contains(Menu.LIST + ".")) {  // TU OK
                        clientInstance = nextLine.substring(0, 1);
                        int clientNumber = Integer.valueOf(clientInstance);

                        if (Server.sockets.containsKey(clientNumber)) {

                            System.out.println(TAG + "sendFileList to " + clientInstance + ". " +
                                    Server.sockets.get(clientNumber).getRemoteSocketAddress());

                            Server.sendFileName(nextLine.substring(6), Server.sockets.get(clientNumber));

                        } else {
                            System.err.println("    askingSocket is null");
                        }

//                        filesNames.add(nextLine.substring(5));
                    }
//                    else if(nextLine.contains(Menu.LIST)){  // TU OK
//                        clientInstance = nextLine.substring(0,1);
//                        int clientNumber = Integer.valueOf(clientInstance);
//
//                        if (!filesNames.isEmpty()) {  // TU OK
//                            if (Server.sockets.containsKey(clientNumber)) {
//
//                                System.out.println(TAG + "sendFileList to " + clientInstance + ". " +
//                                        Server.sockets.get(clientNumber).getRemoteSocketAddress());
//
//                                Server.sendFileList(Server.sockets.get(clientNumber));
//
//                            } else {
//                                System.err.println("    askingSocket is null");
//                            }
//                        } else {
//                            System.err.println(TAG + "filesNames list is empty");
//                        }
//                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
