package com.company;


import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {

        Client client = new Client("Wiktorias-MacBook-Pro.local", 10000);
        client.getFilesList();

        Menu menu = new Menu();
        System.out.println("Welcome to TORent, what would you like to do?");

        menu.showWelcomeOptions();
        String code = menu.getUserWelcomeChoice();
//        Server.connectedHosts = new HashMap<>();
        if (code.equals(Menu.LIST)) {
            client.askServerAboutHosts();
//            client.sendServerCommandToListFiles();
//            client.sendFileListToServer();
        }

//        TODO: Firstly I have to ask server about certain host files, then he ask this host to send them to him.


    }
}
