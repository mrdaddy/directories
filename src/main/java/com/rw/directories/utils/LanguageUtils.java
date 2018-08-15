package com.rw.directories.utils;

public class LanguageUtils {
    private enum SUPPORTED_LANGUAGES {ru, en, by}
    private static final String DEFAULT_LANG = "ru";
    public static String convertToSupportedLang(String lang) {
        String supportedLang = SUPPORTED_LANGUAGES.valueOf(lang.toLowerCase()) != null? lang : DEFAULT_LANG;
        return supportedLang;
    }
}
