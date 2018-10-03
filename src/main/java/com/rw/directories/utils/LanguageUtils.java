package com.rw.directories.utils;

public class LanguageUtils {
    public enum SUPPORTED_LANGUAGES {ru, en, be}
    private static final SUPPORTED_LANGUAGES DEFAULT_LANG = SUPPORTED_LANGUAGES.ru;
    public static String convertToSupportedLang(String lang) {
        if( SUPPORTED_LANGUAGES.valueOf(lang) == null) {
            lang = DEFAULT_LANG.toString();
        }
        String supportedLang = lang;
        return supportedLang;
    }
}
