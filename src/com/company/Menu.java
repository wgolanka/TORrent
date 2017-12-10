package com.company;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {

    public final String EXIT = "EXIT";
    public final static String LIST = "LIST";
    public final static String PULL = "PULL";
    public final static String PUSH = "PUSH";
    public final static String HOSTLIST = "HOSTLIST";


    void showWelcomeOptions() {
        System.out.println("Press 0 to " + EXIT +
                "\nPress 1 to " + LIST + " files" +
                "\nPress 2 to " + PULL + " file" +
                "\nPress 3 to " + PUSH + "files");
    }

    String getUserWelcomeChoice() {
        String code = EXIT;

        switch (getUserInput()) {
            case 0:
                System.out.println("Exit successful");
                code = EXIT;
                break;
            case 1:
                System.out.println("Listing files...");
                code = LIST;
                break;
            case 2:
                System.out.println("Pulling file...");
                code = PULL;
                break;
            case 3:
                System.out.println("Pushing file...");
                code = PUSH;
                break;
            default:
                System.out.println("Please chose between 0-3");
                getUserWelcomeChoice();
                break;
        }
        return code;
    }

    private int choseHost() {
        System.out.println("Chose host which available files you wish to see");
        return 0;
    }


    private int getUserInput() {
        System.out.println("getUserInput()");
        int code = -1;
        Scanner scanner = ScannerCoordinator.getInstance().getScanner();
        try {
            code = scanner.nextInt();
            System.out.println("code: " + code);
        } catch (InputMismatchException e) {
            e.getStackTrace();
            System.out.println("Wrong input.");
        }
        return code;
    }
}
