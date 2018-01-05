package com.company;


import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {

        String userChoice;
        int chosenHost = -1;

        Client client = new Client("Wiktorias-MacBook-Pro.local", 10000);
        Client.instance = args[0];
        client.filesPath = "/Users/wgolanka/Documents/School/#3 semester/SKJ/Tor/TORrent_" + Client.instance;
        client.clientSocket = client.openConnectionWithServer();

        System.out.println("Welcome host " + Client.instance + " what would you like to do?");

        Runnable connectionHandler = new ClientConnectionHandler(client.clientSocket, client);
        new Thread(connectionHandler).start();


        while (true) {

            while (!client.hostIsChosen()) {
                System.out.println("Please chose host which would you like to exchange with");
                System.out.println("Press -1 to update host list");

                do {
                    System.out.println("    ...ABOUT TO CHOSE HOST");
                    client.tryAskServerAboutHosts();
                    chosenHost = Menu.getUserInput();
                }
                while (chosenHost == -1);

                client.sendChosenHostNum(Client.instance, chosenHost);
                Thread.sleep(500);
            }

            System.out.println("    START WHILE AFTER WHILE");
            Menu.showWelcomeOptions();
            userChoice = Menu.getUserWelcomeChoice();


            System.out.println("    BEFORE IF");
            if (userChoice.equals(Menu.LIST)) {
                System.out.println("    IN IF 'LIST'");

                client.tryAskHostForFileNamesFrom(chosenHost);

                Thread.sleep(500);
                System.out.println("    END OF IF");

            } else if (userChoice.contains(Menu.PULL)) {
                System.out.println("    IN IF 'PULL'");
                client.tryAskHostForFileNamesFrom(chosenHost);
                String fileName = Menu.getFileName();
                client.pullFile(Client.instance, chosenHost, fileName);
            } else if (userChoice.equals(Menu.EXIT)) {
                System.out.println("    IN IF 'EXIT'");
                client.finishConnection(Client.instance);
                System.exit(1);
            }
            System.out.println("    OUT OF IF");
        }
    }
}
