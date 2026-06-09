package edu.derryberry.service;

public class FormatUtils {

    public static String formatMoney(double amount) {
        return String.format("$%.2f", amount);
    }

}
