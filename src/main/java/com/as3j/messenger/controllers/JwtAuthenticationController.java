package com.as3j.messenger.controllers;


import com.as3j.messenger.authentication.JwtRequest;
import com.as3j.messenger.authentication.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class JwtAuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public JwtAuthenticationController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping(value = "/login", consumes = "application/json")
    public void createAuthenticationToken(@RequestBody @Valid JwtRequest credentials, HttpServletResponse response) {
        Authentication authInfo = authenticate(credentials);
        final String token = jwtTokenUtil.generateToken(authInfo.getName());
        response.addHeader("Authorization", String.format("Bearer %s", token));
    }

    private Authentication authenticate(JwtRequest credentials) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword()));
    }
}
