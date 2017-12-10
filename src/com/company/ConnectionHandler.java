package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionHandler implements Runnable {

    public ConnectionHandler(Socket clientSocket) {
    }

    @Override
    public void run() {

        Server server = new Server(10000);
        try (ServerSocket serverSocket =
                     new ServerSocket(server.portNumber)) {

            Socket connectionSocket = serverSocket.accept();
            System.out.println("Client accepted: " + connectionSocket.getRemoteSocketAddress().toString());

            BufferedReader inFromClient =
                    new BufferedReader(
                            new InputStreamReader(
                                    connectionSocket.getInputStream())
                    );

            server.checkClientCommand(inFromClient.readLine(), serverSocket);

        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + server.portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }
}
