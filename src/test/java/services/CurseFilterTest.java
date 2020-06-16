package services;

import com.as3j.messenger.curse_filter.EnglishCurseFilter;
import com.as3j.messenger.curse_filter.GeneralCurseFilter;
import com.as3j.messenger.curse_filter.PolishCurseFilter;
import com.as3j.messenger.curse_filter.CurseFilter;
import com.as3j.messenger.curse_filter.dictionaries.EnglishCurseDictionary;
import com.as3j.messenger.curse_filter.dictionaries.PolishCurseDictionary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class CurseFilterTest {
    private CurseFilter curseFilter;

    @BeforeEach
    void setUp() throws IOException {
        var englishCurseDictionary = new EnglishCurseDictionary();
        var polishCurseDictionary = new PolishCurseDictionary();
        curseFilter = new EnglishCurseFilter(
                new PolishCurseFilter(new GeneralCurseFilter(), polishCurseDictionary),
                englishCurseDictionary);
    }

    @Test
    void shouldFilterEnglishText() throws IOException {
        String text = "This is shitty Monday and it is nigger";
        String text_filtered = "This is ****** Monday and it is ******";
        // then
        String filtered =  curseFilter.filterCurseWords(text, "en");
        assertEquals(text_filtered, filtered);
    }

    @Test
    void shouldFilterPolishText() throws IOException {
        String text = "Jarek przyjebał pizdu";
        String text_filtered = "Jarek ********* *****";
        // then
        String filtered =  curseFilter.filterCurseWords(text, "pl");
        assertEquals(text_filtered, filtered);
    }

    @Test
    void shouldNotFilterOtherText() throws IOException {
        String text = "Jarek przyjebał pizdu";
        // then
        String filtered =  curseFilter.filterCurseWords(text, "rs");
        assertEquals(text, filtered);
    }
}
