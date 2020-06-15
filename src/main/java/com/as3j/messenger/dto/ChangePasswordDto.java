package com.as3j.messenger.dto;

import com.as3j.messenger.validation.StrongPassword;

import javax.validation.constraints.NotBlank;

public class ChangePasswordDto {

    @NotBlank
    @StrongPassword
    private String currentPassword;

    @NotBlank
    @StrongPassword
    private String newPassword;

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
