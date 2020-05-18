package com.as3j.messenger.authentication;

import com.as3j.messenger.validation.StrongPassword;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class JwtRequest {
    @NotBlank
    @Email
    private String email;

    @NotNull
    @StrongPassword
    private String password;

    public JwtRequest() {
    }

    public JwtRequest(String email, String password) {
        this.setEmail(email);
        this.setPassword(password);
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
