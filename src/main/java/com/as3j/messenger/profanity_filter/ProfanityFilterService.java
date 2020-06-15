package com.as3j.messenger.profanity_filter;

import java.io.IOException;

public interface ProfanityFilterService {

    String filterCurseWords(String text, String language) throws IOException;
}
