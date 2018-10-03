package com.rw.directories.utils;

import java.text.MessageFormat;

public class DBUtils {

    public static String formatQueryWithParams(String query, String... lang) {
        return MessageFormat.format(query,lang);
    }

    public static boolean toBoolean(Integer value) {
        if(value == 1) {
            return true;
        } else {
            return false;
        }
    }

    public static String toString(boolean value) {
        if(value) {
            return "1";
        } else {
            return "0";
        }
    }

}
