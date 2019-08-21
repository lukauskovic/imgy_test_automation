package com.rest.autotests.utils;

public class CutFirstAndLastChar {

    public static String narrow(String string){
        return string.substring(1, string.length()-1);
    }

}
