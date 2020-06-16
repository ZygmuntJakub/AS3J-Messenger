package com.as3j.messenger.services.impl;

import com.as3j.messenger.common.ApiConfig;
import com.as3j.messenger.services.TranslationService;
import com.google.api.gax.core.CredentialsProvider;
import com.google.cloud.translate.v3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gcp.core.DefaultCredentialsProvider;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TranslationServiceImpl implements TranslationService {
    private TranslationServiceClient client;
    private final ApiConfig apiConfig;
    private final CredentialsProvider credentialsProvider;

    @Autowired
    public TranslationServiceImpl(ApiConfig apiConfig, CredentialsProvider credentialsProvider) {
        this.apiConfig = apiConfig;
        this.credentialsProvider = credentialsProvider;
    }

    @Override
    public List<String> translate(List<String> texts, String language) {
        LocationName parent = LocationName.of(apiConfig.getProjectId(), "global");
        TranslateTextRequest request =
                TranslateTextRequest.newBuilder()
                        .setParent(parent.toString())
                        .setMimeType("text/plain")
                        .setTargetLanguageCode(language)
                        .addAllContents(texts)
                        .build();

        return client.translateText(request).getTranslationsList()
                .stream()
                .map(Translation::getTranslatedText)
                .collect(Collectors.toList());
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
        return response.getLanguagesList()
                .stream()
                .map(DetectedLanguage::getLanguageCode)
                .findFirst()
                .orElse("en");
    }

    @Override
    public String translate(String text, String language) {
        return translate(Collections.singletonList(text), language).get(0);
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
