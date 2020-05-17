package com.as3j.messenger.authentication;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class JwtResponse {

    private final String token;

    public JwtResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return URLEncoder.encode(this.token, StandardCharsets.UTF_8);
    }

}
