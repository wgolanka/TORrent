package com.company;


import java.io.IOException;

public class Main {

    public static boolean ready = false;

    public static void main(String[] args) throws IOException, InterruptedException {

        String userChoice;
        int choseHost;

        Client client = new Client("Wiktorias-MacBook-Pro.local", 10000);
        Client.instance = args[0];
        client.filesPath = "/Users/wgolanka/Documents/School/#3 semester/SKJ/Tor/TORrent_" + Client.instance;
        client.clientSocket = client.openConnectionWithServer();

        System.out.println("Welcome host " + Client.instance + " what would you like to do?");

        Runnable connectionHandler = new ClientConnectionHandler(client.clientSocket, client);
        new Thread(connectionHandler).start();

        while (true) {
            System.out.println("    START WHILE");

            Menu.showWelcomeOptions();

            userChoice = Menu.getUserWelcomeChoice();

            System.out.println("    BEFORE IF");
            if (userChoice.equals(Menu.LIST)) {
                System.out.println("    IN IF 'LIST'");

                client.tryAskServerAboutHosts();

                choseHost = Menu.getUserInput();
                client.sendChosenHostNum(Client.instance, choseHost);

                Thread.sleep(3000);
                System.out.println("    END OF IF");

                // TODO if wrong host is choose, 'while' continues instead of waiting for right host to be chosen

            } else if (userChoice.equals(Menu.EXIT)) {
                System.out.println("    IN IF 'EXIT'");
                client.finishConnection(Client.instance);
                System.exit(1);
            }
            System.out.println("    OUT OF IF");
        }
    }
}
