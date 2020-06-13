package com.as3j.messenger.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class AddUserDto {

    final private static String passwordRegex = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$";

    @NotNull
    @Size(min = 3, max = 255)
    @Email
    private String email;

    @Size(min = 8)
    @Pattern(regexp = passwordRegex, message = "Password not valid")
    private String password;

    @NotNull
    @Size(min = 3, max = 30)
    private String username;

    public AddUserDto() {
    }

    public AddUserDto(@NotNull @Size(min = 3, max = 255) @Email String email,
                      @Size(min = 8) @Pattern(regexp = passwordRegex, message = "Password not valid") String password,
                      @NotNull @Size(min = 3, max = 30) String username) {
        this.email = email;
        this.password = password;
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
