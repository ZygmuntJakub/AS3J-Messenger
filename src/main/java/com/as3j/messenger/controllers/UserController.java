package com.as3j.messenger.controllers;

import com.as3j.messenger.dto.EditUserDTO;
import com.as3j.messenger.exceptions.NoSuchFileException;
import com.as3j.messenger.exceptions.NoSuchUserException;
import com.as3j.messenger.model.entities.User;
import com.as3j.messenger.services.FileService;
import com.as3j.messenger.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;
    private final FileService fileService;

    public UserController(UserService userService, FileService fileService) {
        this.userService = userService;
        this.fileService = fileService;
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void editUser(@PathVariable UUID id, @RequestBody @Valid EditUserDTO editUserDTO) throws NoSuchUserException, NoSuchFileException {
        User user = userService.getById(id);
        editUserDTO.patch(user);
        if(editUserDTO.getPhotoID() != null) {
            fileService.updatePhoto(editUserDTO.getPhotoID(), id);
        }
        userService.update(user);
    }
}
