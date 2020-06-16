package com.as3j.messenger.curse_filter;


import java.io.IOException;
import java.util.List;

public abstract class AbstractCurseFilter implements CurseFilter {

    protected CurseFilter nextFilter;

    @Override
    abstract public String filterCurseWords(String text, String language) throws IOException;

    String getAsteriskString(int num) {
        return new String(new char[num]).replace("\0", "*");
    }
}
