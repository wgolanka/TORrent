package com.company;


public class Main {

    public static void main(String[] args) {

        Client client = new Client("Wiktorias-MacBook-Pro.local", 10000);
        client.instanceNumber = Integer.valueOf(args[0]);
        client.clientSocket = client.openConnection();
        client.getFilesList();

        Menu menu = new Menu();
        System.out.println("Welcome host " + client.instanceNumber + " what would you like to do?");

        menu.showWelcomeOptions();
        String code = menu.getUserWelcomeChoice();

        if (code.equals(Menu.LIST)) {
            if (client.clientSocket != null) {
                client.tryAskServerAboutHosts();
            } else {
                System.out.println("clientSocket is null");
            }

        }

//        TODO: Firstly I have to ask server about certain host files, then he ask this host to send them to him.


    }
}
