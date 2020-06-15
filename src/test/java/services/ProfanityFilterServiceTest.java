package services;

import com.as3j.messenger.profanity_filter.EnglishProfanityFilter;
import com.as3j.messenger.profanity_filter.GeneralProfanityFilter;
import com.as3j.messenger.profanity_filter.PolishProfanityFilter;
import com.as3j.messenger.profanity_filter.ProfanityFilterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ProfanityFilterServiceTest {

    @Autowired
    private ProfanityFilterService profanityFilterService;

    @BeforeEach
    void setUp() {
        profanityFilterService = new EnglishProfanityFilter(new PolishProfanityFilter(new GeneralProfanityFilter()));
    }

    @Test
    void shouldFilterEnglishText() throws IOException {
        String text = "This is shitty Monday and it is nigger";
        String text_filtered = "This is ****ty Monday and it is ******";
        // then
        String filtered =  profanityFilterService.filterCurseWords(text, "en");
        assertEquals(filtered, text_filtered);
    }

    @Test
    void shouldFilterPolishText() throws IOException {
        String text = "Jarek przjebał ryjem";
        String text_filtered = "Jarek prz***** ***em";
        // then
        String filtered =  profanityFilterService.filterCurseWords(text, "pl");
        assertEquals(filtered, text_filtered);
    }

    @Test
    void shouldNotFilterOtherText() throws IOException {
        String text = "Jarek przjebał ryjem";
        // then
        String filtered =  profanityFilterService.filterCurseWords(text, "rs");
        assertEquals(filtered, text);
    }
}
