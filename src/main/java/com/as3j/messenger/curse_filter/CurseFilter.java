package com.as3j.messenger.curse_filter;

import java.io.IOException;

public interface CurseFilter {
    String filterCurseWords(String text, String language) throws IOException;
}
