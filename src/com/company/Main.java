package com.company;


public class Main {

    public static void main(String[] args) {

        Client client = new Client("Wiktorias-MacBook-Pro.local", 10000);
        Client.instanceNumber = args[0];
        Client.filesPath = "/Users/wgolanka/Documents/School/#3 semester/SKJ/Tor/TORrent_" + Client.instanceNumber;
        client.clientSocket = client.openConnectionWithServer();
        client.getFilesList();

        System.out.println("Welcome host " + Client.instanceNumber + " what would you like to do?");

        Runnable connectionHandler = new ClientConnectionHandler(client.clientSocket, client);
        new Thread(connectionHandler).start();

        welcomeChoice(client);
    }

    public static void welcomeChoice(Client client) {

        Menu.showWelcomeOptions();
        String code = Menu.getUserWelcomeChoice();

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
