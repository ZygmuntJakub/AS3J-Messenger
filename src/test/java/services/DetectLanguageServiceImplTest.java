package services;

import com.as3j.messenger.common.ApiConfig;
import com.as3j.messenger.services.DetectLanguageService;
import com.as3j.messenger.services.impl.DetectLanguageServiceImpl;
import com.google.cloud.translate.v3.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class DetectLanguageServiceImplTest {

    private ApiConfig apiConfig;
    private DetectLanguageService detectLanguageService;

    @BeforeEach
    void setUp() {
        apiConfig = mock(ApiConfig.class);
        doReturn("").when(apiConfig).getProjectId();
        detectLanguageService = new DetectLanguageServiceImpl(apiConfig, null);
    }

    @Test
    void shouldDetectLanguage() {
        //given
        String language = "pl";
        var client = mock(TranslationServiceClient.class);
        ReflectionTestUtils.setField(detectLanguageService, "client", client);
        DetectedLanguage detectedLanguage = mock(DetectedLanguage.class);
        DetectLanguageResponse detectLanguageResponse = mock(DetectLanguageResponse.class);
        doReturn(language).when(detectedLanguage).getLanguageCode();
        doReturn(Collections.singletonList(detectedLanguage)).when(detectLanguageResponse).getLanguagesList();
        doReturn(detectLanguageResponse).when(client).detectLanguage(any(DetectLanguageRequest.class));
        var text = "Tekst po polsku";
        //when
        var result = detectLanguageService.detect(text);
        //then
        verify(apiConfig, times(1)).getProjectId();
        verify(client, times(1)).detectLanguage(any(DetectLanguageRequest.class));
        Assertions.assertEquals(language, result);
    }

}