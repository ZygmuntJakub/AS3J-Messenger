package com.as3j.messenger.curse_filter.dictionaries;

import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class EnglishCurseDictionary extends AbstractCurseDictionary{
    public EnglishCurseDictionary() throws IOException {
        super("src/main/resources/curseWords/en/curseWords.json");
    }
}
