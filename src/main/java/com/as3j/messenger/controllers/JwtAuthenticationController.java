package com.as3j.messenger.controllers;


import com.as3j.messenger.authentication.JwtRequest;
import com.as3j.messenger.authentication.JwtTokenUtil;
import com.as3j.messenger.exceptions.NoSuchUserException;
import com.as3j.messenger.model.entities.User;
import com.as3j.messenger.services.UserService;
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
    private final UserService userService;


    @Autowired
    public JwtAuthenticationController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil,
                                       UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
    }

    @PostMapping(value = "/login", consumes = "application/json")
    public User createAuthenticationToken(@RequestBody @Valid JwtRequest credentials,
                                          HttpServletResponse response) throws NoSuchUserException {
        Authentication authInfo = authenticate(credentials);
        final String token = jwtTokenUtil.generateToken(authInfo.getName());
        response.addHeader("Authorization", String.format("Bearer %s", token));
        return userService.getByEmail(authInfo.getName());
    }

    private Authentication authenticate(JwtRequest credentials) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword()));
    }
}
