package com.as3j.messenger.controllers;


import com.as3j.messenger.authentication.JwtRequest;
import com.as3j.messenger.authentication.JwtResponse;
import com.as3j.messenger.authentication.JwtTokenUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class JwtAuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    public JwtAuthenticationController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping(value = "/login", consumes = "application/json")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest credentials) {
        Authentication authInfo = authenticate(credentials);
        final String token = jwtTokenUtil.generateToken(authInfo.getName());
        return ResponseEntity.ok(new JwtResponse(token));
    }

    private Authentication authenticate(JwtRequest credentials) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword()));
    }
}
