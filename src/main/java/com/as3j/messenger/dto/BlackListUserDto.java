package com.as3j.messenger.dto;

import com.as3j.messenger.entities.User;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

public class BlackListUserDto {

    @Type(type = "uuid-char")
    private UUID uuid;


    @NotNull
    @Size(min = 3, max = 30)
    private String username;

    @NotNull
    private String avatarUrl;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public static BlackListUserDto fromUserEntity(User user){
        BlackListUserDto blackListUserDto = new BlackListUserDto();
        blackListUserDto.setUuid(user.getUuid());
        blackListUserDto.setAvatarUrl(user.getAvatarUrl());
        blackListUserDto.setUsername(user.getUsername());
        return blackListUserDto;
    }
}
