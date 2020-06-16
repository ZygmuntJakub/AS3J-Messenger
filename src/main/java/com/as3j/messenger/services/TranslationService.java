package com.as3j.messenger.services;

import java.util.List;

public interface TranslationService {
    String translate(String text, String language);
    List<String> translate(List<String> texts, String language);
    String detect(String text);
}
