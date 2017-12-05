package com.company;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {

    public final String EXIT = "exit";
    public final String PULL = "pull";
    public final String PUSH = "push";
    public final String LIST = "list";


    void showWelcomeOptions() {
        System.out.println("Press 0 to " + EXIT +
                "\nPress 1 to " + LIST + " files" +
                "\nPress 2 to " + PULL + " file" +
                "\nPress 3 to " + PUSH + "files");
    }

    int getUserWelcomeChoice() {
        int code = -1;

        switch (getUserInput()) {
            case 0:
                System.out.println("Exit successful");
                break;
            case 1:
                System.out.println("Listing files...");
                choseHost();
                break;
            case 2:
                System.out.println("Pulling file...");
                break;
            case 3:
                System.out.println("Pushing file...");
                break;
            default:
                System.out.println("Please chose between 0-3");
                getUserWelcomeChoice();
                break;
        }
        return code;
    }

    int choseHost() {
        System.out.println("Chose host which available files you wish to see");
        return 0;
    }


    int getUserInput() {
        System.out.println("getUserInput()");
        int code = -1;
        Scanner scanner = new Scanner(System.in);
        try {
            code = scanner.nextInt();
            System.out.println("code: " + code);
        } catch (InputMismatchException e) {
            e.getStackTrace();
            System.out.println("Wrong input.");
        } finally {
//            scanner.close();
        }
        return code;
    }
}
