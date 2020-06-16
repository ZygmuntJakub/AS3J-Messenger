package com.as3j.messenger.common;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class LanguageCodes {
    public static final Set<String> ISO_LANGUAGES;
    static {
        ISO_LANGUAGES = new HashSet<String>
                (Arrays.asList(Locale.getISOLanguages()));
    }

    public static boolean isValid(String code) {
        return ISO_LANGUAGES.contains(code);
    }
}
