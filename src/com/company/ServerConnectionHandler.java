package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

public class ServerConnectionHandler implements Runnable {
    private Socket clientSocket;
    private static Socket askingSocket;
    public static ArrayList<String> filesNames = new ArrayList<>();

    public ServerConnectionHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        BufferedReader in;
        System.out.println("    ServerConnectionHandler: run");

        try {
            System.out.println("    Client connected: " + clientSocket.getRemoteSocketAddress().toString());
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String nextLine;

            while (true) {
                if ((nextLine = in.readLine()) != null) {
                    System.out.println("    ServerConnectionHandler: " + nextLine);

                    if (nextLine.contains(Menu.HOST + ".")) {
                        System.out.println("    ServerConnectionHandler: " + Server.sockets.get(Integer.valueOf(nextLine.substring(5))).toString());

                        Server.askHostToSendFileList(Server.sockets.get(Integer.valueOf(nextLine.substring(5))));
                        askingSocket = clientSocket;

                    } else if (nextLine.contains(Menu.LIST + ".")) {
                        filesNames.add(nextLine.substring(5));
                    }

                    switch (nextLine) {
                        case Menu.HOSTLIST:
                            System.out.println("    ServerConnectionHandler: Switch HOSTLIST");
                            Server.sendHostList(clientSocket);
                            break;
                        case Menu.LIST:
                            System.out.println("    ServerConnectionHandler: Switch LIST");
                            if (!filesNames.isEmpty()) {
                                if (askingSocket != null) {
                                    Server.sendFileList(askingSocket);
                                } else {
                                    System.err.println("    askingSocket is null");
                                }
                            } else {
                                System.err.println("    ServerConnectionHandler: filesNames list is empty");
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
