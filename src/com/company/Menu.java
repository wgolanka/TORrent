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
                System.out.println("Exit successful");
                code = EXIT;
                break;
            case 1:
                System.out.println("Chose host which available files you wish to see");
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

//        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Scanner scanner = ScannerCoordinator.getInstance().getScanner();

        try {

//            code = Integer.parseInt(br.readLine());
            code = scanner.nextInt();

            System.out.println("    code: " + code);

        } catch (InputMismatchException e) {
            e.getStackTrace();
            System.out.println("    Wrong input.");
        }
        return code;
    }
}
