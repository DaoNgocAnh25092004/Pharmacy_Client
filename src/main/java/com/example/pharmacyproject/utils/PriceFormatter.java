package com.example.pharmacyproject.utils;

import java.text.DecimalFormat;
import java.text.ParseException;

public class PriceFormatter {
    public static String formatPrice(double price) {
        DecimalFormat formatter = new DecimalFormat("###,###,###.##");
        return formatter.format(price) + "đ";
    }

    public static double parsePrice(String formattedPrice) throws ParseException {
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        formattedPrice = formattedPrice.replace("đ", "").trim();
        Number number = formatter.parse(formattedPrice);
        return number.doubleValue();
    }

    public static String formatPriceToRegularString(String formattedPrice) {
        // Remove the "đ" character and all the commas
        return formattedPrice.replace("đ", "").replace(",", "");
    }
}
