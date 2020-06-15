package com.as3j.messenger.profanity_filter;

import org.springframework.stereotype.Service;

@Service
public class GeneralProfanityFilter extends AbstractProfanityFilter{

    @Override
    public String filterCurseWords(String text, String language) {
        return text;
    }

}
