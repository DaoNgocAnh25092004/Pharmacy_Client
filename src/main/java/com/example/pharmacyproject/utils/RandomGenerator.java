package com.example.pharmacyproject.utils;

import java.util.Random;

public class RandomGenerator {
    private static final Random random = new Random(System.currentTimeMillis());

    public static int genInt(int from, int to) {
        if (from >= to) {
            throw new IllegalArgumentException("Invalid range: 'from' must be less than 'to'");
        }
        return random.nextInt(to - from) + from;
    }

    public static String genString(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("Invalid length: 'length' must be greater than 0");
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append((char) genInt('a', 'z' + 1));
        }
        return sb.toString();
    }

    public static String genChiTietPhieuNhapID() {
        return "CTPN" + genString(10);
    }

    public static String genPhieuNhapID() {
        return "PN" + genString(10);
    }
}