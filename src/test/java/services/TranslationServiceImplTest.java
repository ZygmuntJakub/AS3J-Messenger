package services;

import com.as3j.messenger.common.ApiConfig;
import com.as3j.messenger.services.impl.TranslationServiceImpl;
import com.google.cloud.translate.v3.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TranslationServiceImplTest {
    private ApiConfig apiConfig;
    private TranslationServiceImpl translationService;

    @BeforeEach
    void setUp() {
        apiConfig = mock(ApiConfig.class);
        doReturn("").when(apiConfig).getProjectId();
        translationService = new TranslationServiceImpl(apiConfig, null);
    }

    @Test
    void shouldTranslateMessages() {
        //given
        var expectedOutput = "Test2";
        var client = mock(TranslationServiceClient.class);
        ReflectionTestUtils.setField(translationService, "client", client);
        var translation = mock(Translation.class);
        doReturn(expectedOutput).when(translation).getTranslatedText();
        var translationResponse = mock(TranslateTextResponse.class);
        doReturn(Collections.singletonList(translation)).when(translationResponse).getTranslationsList();
        doReturn(translationResponse).when(client).translateText(any(TranslateTextRequest.class));
        var text = "Test";
        //when
        var result = translationService.translate(Collections.singletonList(text), "en");
        //then
        verify(apiConfig, times(1)).getProjectId();
        verify(client, times(1)).translateText(any(TranslateTextRequest.class));
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(expectedOutput, result.get(0));
    }

    @Test
    void shouldDetectLanguage() {
        //given
        String language = "pl";
        var client = mock(TranslationServiceClient.class);
        ReflectionTestUtils.setField(translationService, "client", client);
        DetectedLanguage detectedLanguage = mock(DetectedLanguage.class);
        DetectLanguageResponse detectLanguageResponse = mock(DetectLanguageResponse.class);
        doReturn(language).when(detectedLanguage).getLanguageCode();
        doReturn(Collections.singletonList(detectedLanguage)).when(detectLanguageResponse).getLanguagesList();
        doReturn(detectLanguageResponse).when(client).detectLanguage(any(DetectLanguageRequest.class));
        var text = "Tekst po polsku";
        //when
        var result = translationService.detect(text);
        //then
        verify(apiConfig, times(1)).getProjectId();
        verify(client, times(1)).detectLanguage(any(DetectLanguageRequest.class));
        Assertions.assertEquals(language, result);
    }
}
