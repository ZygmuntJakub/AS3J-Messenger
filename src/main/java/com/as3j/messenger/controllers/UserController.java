package com.as3j.messenger.controllers;

import com.as3j.messenger.dto.ChangePasswordDto;
import com.as3j.messenger.dto.EditUserDto;
import com.as3j.messenger.exceptions.NoSuchFileException;
import com.as3j.messenger.exceptions.NoSuchUserException;
import com.as3j.messenger.exceptions.WrongCurrentPasswordException;
import com.as3j.messenger.dto.AddUserDto;
import com.as3j.messenger.exceptions.UserWithSuchEmailExistException;
import com.as3j.messenger.model.entities.User;
import com.as3j.messenger.services.FileService;
import com.as3j.messenger.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;
    private final FileService fileService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService, FileService fileService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.fileService = fileService;
        this.passwordEncoder = passwordEncoder;
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


    @PatchMapping(path = "password")
    public void changePassword(@RequestBody @Valid ChangePasswordDto changePasswordDto,
                               @AuthenticationPrincipal UserDetails userDetails)
            throws NoSuchUserException, WrongCurrentPasswordException {

        User user = userService.getByEmail(userDetails.getUsername());
        userService.changePassword(user, changePasswordDto);
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public void registerUser(@RequestBody @Valid AddUserDto addUserDto) throws UserWithSuchEmailExistException {
        userService.create(convertToUser(addUserDto));
    }

    private User convertToUser(AddUserDto addUserDto) {
        User user = new User();
        user.setEmail(addUserDto.getEmail());
        user.setUsername(addUserDto.getUsername());
        user.setPassword(passwordEncoder.encode(addUserDto.getPassword()));
        return user;
    }
}
