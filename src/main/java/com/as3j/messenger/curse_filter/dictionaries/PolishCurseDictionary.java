package com.as3j.messenger.curse_filter.dictionaries;

import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class PolishCurseDictionary extends AbstractCurseDictionary{
    public PolishCurseDictionary() throws IOException {
        super("src/main/resources/curseWords/pl/curseWords.json");
    }
}
