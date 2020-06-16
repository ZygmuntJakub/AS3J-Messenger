package com.as3j.messenger.curse_filter;

import org.springframework.stereotype.Component;

@Component
public class GeneralCurseFilter extends AbstractCurseFilter {

    @Override
    public String filterCurseWords(String text, String language) {
        return text;
    }

}
