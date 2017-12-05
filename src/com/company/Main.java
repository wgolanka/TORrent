package com.company;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Menu menu = new Menu();

        System.out.println("Welcome to TORent, what would you like to do?");
        int code = 0;

        menu.showWelcomeOptions();
        code = menu.getUserWelcomeChoice();


//        while(true){
//            Scanner scanner = new Scanner(System.in);
//
//            try{
//                System.out.println("...");
//                code = scanner.nextInt();
//                System.out.println(code);
//            }catch (Exception e){
//                e.getStackTrace();
//            }finally {
//                scanner.close();
//            }
//        }

    }
}
