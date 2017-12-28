package com.company;


public class Main {

    public static void main(String[] args) {

        Client client = new Client("Wiktorias-MacBook-Pro.local", 10000);
        client.instanceNumber = Integer.valueOf(args[0]);
        client.clientSocket = client.openConnectionWithServer();
        client.getFilesList();

        Menu menu = new Menu();
        System.out.println("Welcome host " + client.instanceNumber + " what would you like to do?");

        menu.showWelcomeOptions();
        String code = menu.getUserWelcomeChoice();

        Runnable connectionHandler = new ClientConnectionHandler(client.clientSocket, client);
        new Thread(connectionHandler).start();

        if (code.equals(Menu.LIST)) {
            if (client.clientSocket != null) {
                client.tryAskServerAboutHosts();
            } else {
                System.err.println("Client.clientSocket is null");
            }


        }


    }
}
