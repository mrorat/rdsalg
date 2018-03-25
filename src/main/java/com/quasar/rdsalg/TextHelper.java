package com.quasar.rdsalg;

public class TextHelper {

    public static String capitalizeFirstLetter(String input) {
        if (input.length() > 0)
            return input.substring(0, 1).toUpperCase() + (input.length() > 1 ? input.substring(1) : "");
        throw new RuntimeException("Unable to capitalize null or empty string.");
    }
}
