package services;

import com.as3j.messenger.services.ProfanityFilterService;
import com.as3j.messenger.services.impl.ProfanityFilterServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProfanityFilterServiceImplTest {

    private ProfanityFilterService profanityFilterService;

    @BeforeEach
    void setUp() {
        profanityFilterService = new ProfanityFilterServiceImpl();
    }

    @Test
    void shouldThrowExceptionWhenChatIsNotFound() {
        String text = "This is shitty Monday and it is nigger";
        String text_filtered = "This is ****** Monday and it is ******";
        // then
        String filtered =  profanityFilterService.filterProfanity(text);
        assertEquals(filtered, text_filtered);
    }
}
