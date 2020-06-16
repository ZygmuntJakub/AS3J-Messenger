package com.as3j.messenger.services.impl;

import com.as3j.messenger.common.ApiConfig;
import com.as3j.messenger.services.DetectLanguageService;
import com.google.api.gax.core.CredentialsProvider;
import com.google.cloud.translate.v3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;

@Service
public class DetectLanguageServiceImpl implements DetectLanguageService {

    private TranslationServiceClient client;
    private final ApiConfig apiConfig;
    private final CredentialsProvider credentialsProvider;

    @Autowired
    public DetectLanguageServiceImpl(ApiConfig apiConfig, CredentialsProvider credentialsProvider) {
        this.apiConfig = apiConfig;
        this.credentialsProvider = credentialsProvider;
    }
    @Override
    public String detect(String text) {
        LocationName parent = LocationName.of(apiConfig.getProjectId(), "global");
        DetectLanguageRequest request =
                DetectLanguageRequest.newBuilder()
                        .setParent(parent.toString())
                        .setMimeType("text/plain")
                        .setContent(text)
                        .build();

        DetectLanguageResponse response = client.detectLanguage(request);
        return response.getLanguagesList().stream().map(DetectedLanguage::getLanguageCode).findFirst().orElse("en");
    }


    @PostConstruct
    public void initialize() throws IOException {
        TranslationServiceSettings settings = TranslationServiceSettings
                .newBuilder()
                .setCredentialsProvider(credentialsProvider)
                .build();
        client = TranslationServiceClient.create(settings);
    }

    @PreDestroy
    public void clean() {
        client.close();
    }
}
