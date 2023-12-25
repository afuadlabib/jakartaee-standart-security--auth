package com.labcenter.security;

public class Encrypt {

    public static String encrypt(String planPassword) {
        String result = new String();

        char[] chars = planPassword.toCharArray();
        for (char b : chars) {
            var c = b;
            var d = c;
            var e = b;
            b += 7;
            c -= 20;
            d += 5;
            e -= 6;

            result += b;
            result += c;
            result += d;
            result += e;
        }

        return result;
    }

    public static String decrypt(String encString) {
        String result = new String();
        char[] chars = encString.toCharArray();
        for (var i = 0; i < chars.length; i++) {
            if (i % 4 == 0) {
                char b = chars[i];
                b -= 7;
                result += b;
            }
        }
        return result;
    }
}
