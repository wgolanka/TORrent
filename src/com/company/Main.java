package com.company;


public class Main {

    public static void main(String[] args) {

        Client client = new Client("Wiktorias-MacBook-Pro.local", 10000);
        Client.instanceNumber = Integer.valueOf(args[0]);
        client.clientSocket = client.openConnectionWithServer();
        client.getFilesList();

        Menu menu = new Menu();
        System.out.println("Welcome host " + Client.instanceNumber + " what would you like to do?");

        Runnable connectionHandler = new ClientConnectionHandler(client.clientSocket, client);
        new Thread(connectionHandler).start();

        menu.showWelcomeOptions();
        String code = menu.getUserWelcomeChoice();


        if (code.equals(Menu.LIST)) {
            System.out.println("    Menu: LIST chosen");
            if (client.clientSocket != null) {
                System.out.println("    Menu: asking server about host list");
                client.tryAskServerAboutHosts();
            } else {
                System.err.println("    Client.clientSocket is null");
            }

        }


    }
}
