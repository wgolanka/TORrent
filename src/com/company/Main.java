package com.company;


import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        Client client = new Client("Wiktorias-MacBook-Pro.local", 10000);
        client.getFilesList();

        Menu menu = new Menu();
        System.out.println("Welcome to TORent, what would you like to do?");
        int code = 0;

        menu.showWelcomeOptions();
        code = menu.getUserWelcomeChoice();


    }
}
