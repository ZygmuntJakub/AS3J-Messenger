package com.as3j.messenger.curse_filter.dictionaries;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public abstract class AbstractCurseDictionary {
    private final Set<String> entries;

    public AbstractCurseDictionary(String path) throws IOException {
        String[] words = new ObjectMapper().readValue(
                Paths.get(path).toFile(), String[].class);
        entries = new HashSet<>();
        entries.addAll(Arrays.asList(words));
    }

    public boolean contains(String word) {
        return entries.contains(word);
    }
}
