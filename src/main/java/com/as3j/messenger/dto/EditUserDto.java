package com.as3j.messenger.dto;

import com.as3j.messenger.model.entities.User;

import javax.validation.constraints.Size;
import java.util.Optional;
import java.util.UUID;

public class EditUserDto {
    @Size(min = 3, max = 30)
    private String username;

    private UUID photoID;

    public EditUserDto() {
    }

    public EditUserDto(@Size(min = 3, max = 30) String username, UUID photoID) {
        this.username = username;
        this.photoID = photoID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Optional<UUID> getPhotoID() {
        return Optional.ofNullable(photoID);
    }

    public void setPhotoID(UUID photoID) {
        this.photoID = photoID;
    }

    public void patch(User user) {
        if (username != null) {
            user.setUsername(username);
        }
        if (photoID != null) {
            user.setAvatarPresent(true);
        }
    }
}
