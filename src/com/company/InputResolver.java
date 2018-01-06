package com.company;

public class InputResolver {

    // In this protocol the first sign of String is an instance number of asking client.
    public static String getClientInstance(String fromClient) {
        return fromClient.substring(0, 1);
    }

    public static int getClientInstanceInt(String fromClient) {
        return Integer.valueOf(getClientInstance(fromClient));
    }

    // InstanceNumber + "LIST/" tag + filename, substring starts at 6
    public static String getFileName(String fromClient) {
        return fromClient.substring(6);
    }

    // InstanceNumber + "HOST" / "PULL" tag + chosen host number
    public static int getChosenHostNumber(String fromClient) {
        System.out.println("wtf: " + fromClient.substring(5, 6));
        return Integer.valueOf(fromClient.substring(5, 6));
    }
}
