package com.as3j.messenger.controllers;

import com.as3j.messenger.authentication.UserDetailsImpl;
import com.as3j.messenger.dto.EditUserDTO;
import com.as3j.messenger.exceptions.NoSuchFileException;
import com.as3j.messenger.exceptions.NoSuchUserException;
import com.as3j.messenger.model.entities.User;
import com.as3j.messenger.services.FileService;
import com.as3j.messenger.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;
    private final FileService fileService;

    public UserController(UserService userService, FileService fileService) {
        this.userService = userService;
        this.fileService = fileService;
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void editUser(@RequestBody @Valid EditUserDTO editUserDTO, @AuthenticationPrincipal UserDetailsImpl userDetails) throws NoSuchUserException, NoSuchFileException {
        User user = userService.getByEmail(userDetails.getUsername());
        editUserDTO.patch(user);
        if(editUserDTO.getPhotoID() != null) {
            fileService.updatePhoto(editUserDTO.getPhotoID(), user.getUuid());
        }
        userService.update(user);
    }
}
