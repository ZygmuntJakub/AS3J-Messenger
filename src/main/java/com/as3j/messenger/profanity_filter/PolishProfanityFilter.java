package com.as3j.messenger.profanity_filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;

@Service
public class PolishProfanityFilter extends AbstractProfanityFilter {

    public PolishProfanityFilter(@Qualifier("generalProfanityFilter") ProfanityFilterService profanityFilterService){
        this.profanityFilterService = profanityFilterService;
    }

    @Override
    public String filterCurseWords(String text, String language) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        this.profanityWords = Arrays.asList(
                objectMapper.readValue(Paths.get("src/main/resources/profanityWords/pl/curseWords.json").toFile(), String[].class));
        if (language.equals("pl")) {
            for (String word : this.profanityWords) {
                if (text.toLowerCase().contains(word))
                    text = text.replaceAll( "(?i)"+ word, stringOfAsterisks(word.length()));
            }
            return text;
        } else
            return this.profanityFilterService.filterCurseWords(text, language);
    }
}
