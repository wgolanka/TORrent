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

            System.out.println("Please chose host which would you like to exchange with");
            System.out.println("Press -1 to update host list");

            client.tryAskServerAboutHosts();
            chosenHost = Menu.getUserInput();

            if (chosenHost == -1) {
                continue;
            }

            client.sendChosenHostNum(Client.instance, chosenHost);

            System.out.println("    START WHILE AFTER WHILE");

            Menu.showWelcomeOptions();
            userChoice = Menu.getUserWelcomeChoice();
//          TODO: loop continues after user choose wrong host and
//          it generates nullptr excep in  ServerConnectionHandler 95

            System.out.println("    BEFORE IF");
            if (userChoice.equals(Menu.LIST)) {
                System.out.println("    IN IF 'LIST'");

                client.tryAskHostForFileNamesFrom(chosenHost);

            } else if (userChoice.contains(Menu.PULL)) {

                System.out.println("    IN IF 'PULL'");
                client.tryAskHostForFileNamesFrom(chosenHost);

                System.out.println("Type file name (with its extension) which would you like to download");
                String fileName = Menu.getFileName();

                client.pullFile(Client.instance, chosenHost, fileName);

            } else if (userChoice.contains(Menu.PUSH)) {
                System.out.println("    IN IF 'PUSH'");

                client.tryAskHostForFileNamesFrom(Integer.valueOf(args[0]));

                System.out.println("Type file name (with its extension) which would you like to push to the chosen host");
                String fileName = Menu.getFileName();
                String command = String.valueOf(chosenHost);

                client.sendFileToServer(command, fileName);
            } else if (userChoice.equals(Menu.EXIT)) {
                System.out.println("    IN IF 'EXIT'");
                client.finishConnection(Client.instance);
                System.exit(1);
            }

            Thread.sleep(3000);

            System.out.println("    OUT OF IF");
        }
    }
}
