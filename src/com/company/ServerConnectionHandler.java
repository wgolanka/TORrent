package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerConnectionHandler implements Runnable {
    private Socket clientSocket;

    public ServerConnectionHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        BufferedReader in;
        System.out.println("ServerConnectionHandler: run");

        try {
            System.out.println("Client connected: " + clientSocket.getRemoteSocketAddress().toString());
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String nextLine;
            while (true) {
                if ((nextLine = in.readLine()) != null) {
                    System.out.println("ServerConnectionHandler: " + nextLine);

                    switch (nextLine) {
                        case Menu.HOSTLIST:
                            ServerMain.sendHostList(clientSocket);
                            break;
                    }
                }

            }

//            System.out.println("connectionhandler after");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
