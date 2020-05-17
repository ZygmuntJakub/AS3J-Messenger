package com.as3j.messenger.authentication;

import java.io.Serializable;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class JwtResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046845L;

    private final String token;

    public JwtResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return URLEncoder.encode(this.token, StandardCharsets.UTF_8);
    }

}
