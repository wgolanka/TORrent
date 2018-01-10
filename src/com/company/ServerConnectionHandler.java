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
        clientInstance = InputResolver.getClientInstance(fromClient);
        System.out.println(TAG + "Send host list to " + clientInstance + " host");
        Server.sendHostList(clientInstance, clientSocket);
    }


    private void sendFileName(String fromClient) throws IOException {

        clientInstance = InputResolver.getClientInstance(fromClient);
        int clientNumber = Integer.valueOf(clientInstance);

        if (Server.sockets.containsKey(clientNumber)) {

            System.out.println(TAG + "sendFileList to " + clientInstance + "/ " +
                    Server.sockets.get(clientNumber).getRemoteSocketAddress());

            Server.sendFileName(InputResolver.getFileName(fromClient), Server.sockets.get(clientNumber));

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
                    System.out.println(TAG + nextLine);

                    if (nextLine.contains(Menu.CLIENTS)) {
                        sendHostList(nextLine);

                    } else if (nextLine.contains(Menu.HOST)) {


                    } else if (nextLine.contains(Menu.LIST + "/")) {
                        sendFileName(nextLine);

                    } else if (nextLine.contains(Menu.FINISHED)) {

                        Server.sendFinishCommand(Server.sockets.get(
                                InputResolver.getClientInstanceInt(nextLine)));

                    } else if (nextLine.contains(Menu.FILENAMES)) {
                        String array[] = nextLine.split("/");
                        Server.askHostToSendFileNames(
                                InputResolver.getClientInstance(nextLine),
                                Server.sockets.get(Integer.valueOf(array[1])));

                    } else if (nextLine.contains(Menu.PULL)) {
                        String array[] = nextLine.split("/");
                        Server.askHostToSendFile(
                                InputResolver.getClientInstance(nextLine),
                                Server.sockets.get(
                                        InputResolver.getChosenHostNumber(nextLine)),
                                array[1]);

                    } else if (nextLine.contains(Menu.EXIT)) {
                        System.out.println(TAG + " deleting " + InputResolver.getClientInstance(nextLine));
                        Server.sockets.remove(InputResolver.getClientInstanceInt(nextLine));
                        Server.letClientKnowItCanExit(clientSocket);

                    } else if (nextLine.contains(Menu.PUSH)) {
                        Server.sendFile(InputResolver.getClientInstance(nextLine), nextLine);

                    } else if (nextLine.contains(Menu.ERROR)) {
                        String commandAndMessage[] = nextLine.split("/");

                        Server.sendErrorMsg(Server.sockets.get(
                                InputResolver.getClientInstanceInt(nextLine)), commandAndMessage[1]);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
