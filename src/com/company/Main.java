package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Main {

    public static void main(String[] args) throws IOException {


        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);


//        try{
//            InetAddress address = InetAddress.getByName(args[1]);
//            boolean reachable = address.isReachable(portNumber);
//
//            System.out.println("Is host reachable? " + reachable);
//        } catch (Exception e){
//            e.printStackTrace();
//        }

        try (

                Socket echoSocket = new Socket(hostName, portNumber);


                PrintWriter out =
                        new PrintWriter(echoSocket.getOutputStream(), true);
                BufferedReader in =
                        new BufferedReader(
                                new InputStreamReader(echoSocket.getInputStream()));
                BufferedReader stdIn =
                        new BufferedReader(
                                new InputStreamReader(System.in))
        ) {


            String userInput;
            while ((userInput = stdIn.readLine()) != null) {
                out.println(userInput);
                System.out.println("echo: " + in.readLine());
            }

        }

    }
}
