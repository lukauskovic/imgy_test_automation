package com.rest.autotests.utils;

import java.util.Random;

public class RandomGenerator {

    public static String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    public static StringBuilder salt = new StringBuilder();
    public static java.util.Random rnd = new java.util.Random();

    public static String randMail() {
        while (salt.length() < 10) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr +  "@test.test";
    }

    public static String randUsername() {
        while (salt.length() < 10) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }

    public static String randPass(){
        while (salt.length() < 10) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String randPass = salt.toString();
        return randPass;
    }
}
