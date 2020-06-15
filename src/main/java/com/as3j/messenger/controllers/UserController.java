package com.as3j.messenger.controllers;

import com.as3j.messenger.dto.ChatDto;
import com.as3j.messenger.dto.EditUserDto;
import com.as3j.messenger.dto.UserDto;
import com.as3j.messenger.exceptions.NoSuchFileException;
import com.as3j.messenger.exceptions.NoSuchUserException;
import com.as3j.messenger.model.entities.User;
import com.as3j.messenger.services.FileService;
import com.as3j.messenger.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;
    private final FileService fileService;

    @Autowired
    public UserController(UserService userService, FileService fileService) {
        this.userService = userService;
        this.fileService = fileService;
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void editUser(@RequestBody @Valid EditUserDto editUserDto,
                         @AuthenticationPrincipal UserDetails userDetails) throws NoSuchUserException, NoSuchFileException {
        User user = userService.getByEmail(userDetails.getUsername());
        editUserDto.patch(user);
        if (editUserDto.getPhotoID().isPresent()) {
            fileService.updatePhoto(editUserDto.getPhotoID().get(), user.getUuid());
        }
        userService.update(user);
    }

    @GetMapping
    @ResponseBody
    public List<UserDto> getUsers(@AuthenticationPrincipal UserDetails userDetails) throws NoSuchUserException {
        User user = userService.getByEmail(userDetails.getUsername());
        return userService.getAll().stream()
                .filter(user1 -> !user1.getEmail().equals(user.getEmail()))
                .map(UserDto::fromUserEntity).collect(Collectors.toList());
    }
}
