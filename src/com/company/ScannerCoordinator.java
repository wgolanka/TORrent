package com.company;


import java.util.Scanner;

public class ScannerCoordinator {
    private static Scanner scannerInstance;

    private ScannerCoordinator() {
    }

    public static Scanner getInstance() {
        if (scannerInstance == null) {
            scannerInstance = new Scanner(System.in);
        }
        return scannerInstance;
    }
}
