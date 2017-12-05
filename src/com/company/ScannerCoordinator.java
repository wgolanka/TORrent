package com.company;


import java.util.Scanner;

public class ScannerCoordinator {

    private static ScannerCoordinator scannerInstance;
    private Scanner scanner;

    private ScannerCoordinator() {
        scanner = new Scanner(System.in);
    }

    public static ScannerCoordinator getInstance() {
        if (scannerInstance == null) {
            scannerInstance = new ScannerCoordinator();
        }
        return scannerInstance;
    }

    public Scanner getScanner() {
        return scannerInstance.scanner;
    }
}
