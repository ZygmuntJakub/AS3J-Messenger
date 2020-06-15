package com.as3j.messenger.common;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class MyPasswordEncoder {

    @Value("${jwt.BCryptStrength}")
    private int BCryptStrength;

    public String encode(String password) {
        return new BCryptPasswordEncoder(BCryptStrength).encode(password);
    }
}
