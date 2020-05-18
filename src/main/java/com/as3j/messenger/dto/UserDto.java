package com.as3j.messenger.dto;

import com.as3j.messenger.model.entities.User;

import java.util.UUID;

public class UserDto {

    private UUID uuid;

    private String username;

    private boolean avatarPresent;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getUsername() {
        return username;
    }

    public boolean isAvatarPresent() {
        return avatarPresent;
    }

    public void setAvatarPresent(boolean avatarPresent) {
        this.avatarPresent = avatarPresent;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public static UserDto fromUserEntity(User user) {
        UserDto userDto = new UserDto();
        userDto.setUuid(user.getUuid());
        userDto.setAvatarPresent(user.getAvatarPresent());
        userDto.setUsername(user.getUsername());
        return userDto;
    }
}
