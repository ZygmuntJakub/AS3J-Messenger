package com.as3j.messenger.curse_filter;

import com.as3j.messenger.curse_filter.dictionaries.PolishCurseDictionary;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class PolishCurseFilter extends AbstractCurseFilter {

    private final PolishCurseDictionary curseDictionary;

    public PolishCurseFilter(@Qualifier("generalCurseFilter") CurseFilter curseFilter,
                             PolishCurseDictionary curseDictionary){
        this.nextFilter = curseFilter;
        this.curseDictionary = curseDictionary;
    }

    @Override
    public String filterCurseWords(String text, String language) throws IOException {
        if (language.equals("pl")) {
            for (String word : text.split("\\s")) {
                if (curseDictionary.contains(word.toLowerCase()))
                    text = text.replaceAll( "(?i)"+ word, getAsteriskString(word.length()));
            }
            return text;
        } else
            return this.nextFilter.filterCurseWords(text, language);
    }
}
