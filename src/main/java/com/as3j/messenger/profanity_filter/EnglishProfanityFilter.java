package com.as3j.messenger.profanity_filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;

@Primary
@Service
public class EnglishProfanityFilter extends AbstractProfanityFilter{

    @Autowired
    @Qualifier("polishProfanityFilter")
    private ProfanityFilterService profanityFilterService;

    public EnglishProfanityFilter(@Qualifier("polishProfanityFilter") ProfanityFilterService profanityFilterService) {
        this.profanityFilterService = profanityFilterService;
    }

    @Override
    public String filterCurseWords(String text, String language) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        this.profanityWords = Arrays.asList(objectMapper.
                readValue(Paths.get("src/main/resources/profanityWords/en/curseWords.json").toFile(), String[].class));
        if (language.equals("en")) {
            for (String word : this.profanityWords) {
                if (text.toLowerCase().contains(word))
                    text = text.replaceAll( "(?i)" + word, stringOfAsterisks(word.length()));
            }
            return text;
        } else
            return this.profanityFilterService.filterCurseWords(text, language);
    }

}
