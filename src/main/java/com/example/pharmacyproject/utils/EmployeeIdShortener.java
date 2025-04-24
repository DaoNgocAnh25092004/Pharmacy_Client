package com.example.pharmacyproject.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EmployeeIdShortener {
    public static String stringShortener(String longId) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(longId.getBytes());
            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString().substring(0, 8);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
