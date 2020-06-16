package com.as3j.messenger.curse_filter;

import com.as3j.messenger.curse_filter.dictionaries.EnglishCurseDictionary;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Primary
@Component
public class EnglishCurseFilter extends AbstractCurseFilter {

    private final EnglishCurseDictionary curseDictionary;

    public EnglishCurseFilter(@Qualifier("polishCurseFilter") CurseFilter curseFilter,
                              EnglishCurseDictionary curseDictionary) {
        this.nextFilter = curseFilter;
        this.curseDictionary = curseDictionary;
    }

    @Override
    public String filterCurseWords(String text, String language) throws IOException {
        if (language.equals("en")) {
            for (String word : text.split("\\s")) {
                if (curseDictionary.contains(word.toLowerCase()))
                    text = text.replaceAll( "(?i)" + word, getAsteriskString(word.length()));
            }
            return text;
        } else
            return this.nextFilter.filterCurseWords(text, language);
    }

}
