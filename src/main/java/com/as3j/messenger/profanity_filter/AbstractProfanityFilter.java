package com.as3j.messenger.profanity_filter;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractProfanityFilter implements ProfanityFilterService {

    ProfanityFilterService profanityFilterService;

    List<String> profanityWords;

    @Override
    abstract public String filterCurseWords(String text, String language) throws IOException;

    String stringOfAsterisks(int num) {
        return new String(new char[num]).replace("\0", "*");
    }
}
