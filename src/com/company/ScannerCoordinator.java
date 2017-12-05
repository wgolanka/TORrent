package com.company;


public class ScannerCoordinator {
    private final static ScannerCoordinator scannerInstance = new ScannerCoordinator();

    private ScannerCoordinator() {
    }

    public static ScannerCoordinator getInstance() {
        return scannerInstance;
    }
}
