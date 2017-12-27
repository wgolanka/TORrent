package com.company;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {
    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(10000);

        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                Runnable connectionHandler = new ServerConnectionHandler(clientSocket);
                new Thread(connectionHandler).start();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
