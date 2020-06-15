package com.as3j.messenger.services.impl;

import com.as3j.messenger.services.ProfanityFilterService;
import org.springframework.web.client.RestTemplate;

public class ProfanityFilterServiceImpl implements ProfanityFilterService {

    private static final String FILTER_API = "https://www.purgomalum.com/service/plain?text=";

    @Override
    public String filterProfanity(String text) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(FILTER_API + text, String.class);
    }
}
