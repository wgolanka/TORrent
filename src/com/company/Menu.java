package com.company;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {


    public final static String EXIT = "EXIT";
    public final static String LIST = "LIST";
    public final static String PULL = "PULL";
    public final static String PUSH = "PUSH";
    public final static String CLIENTS = "CLIENTS";
    public final static String HOST = "HOST";
    public final static String FINISHED = "FINISHED";
    public static final String FILENAMES = "FILENAMES";
    public static final String ERROR = "ERROR";


    static void showWelcomeOptions() {
        System.out.println("Press 0 to " + EXIT +
                "\nPress 1 to " + LIST + " files" +
                "\nPress 2 to " + PULL + " file" +
                "\nPress 3 to " + PUSH + " files");
    }

    static String getUserWelcomeChoice() {
        String code = EXIT;

        switch (getUserInput()) {
            case 0:
                System.out.println("Exiting");
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

    public static int getUserInput() {
        System.out.println("    Menu: getUserInput()");
        int code = -1;

        Scanner scanner = ScannerCoordinator.getInstance().getScanner();

        try {
            code = scanner.nextInt();
            System.out.println("    code: " + code);

        } catch (InputMismatchException e) {
            e.getStackTrace();
            System.out.println("    Wrong input.");
        }
        return code;
    }

    public static String getFileName() {

        System.out.println("    Menu: getFileName()");

        String input = "";

        Scanner scanner = ScannerCoordinator.getInstance().getScanner();

        try {
            input = scanner.next();
            System.out.println("    StringInput: " + input);

        } catch (InputMismatchException e) {
            e.getStackTrace();
            System.out.println("    Wrong input.");
        }
        return input;
    }
}
