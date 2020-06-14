package com.as3j.messenger.controllers;

import com.as3j.messenger.common.MyPasswordEncoder;
import com.as3j.messenger.dto.AddUserDto;
import com.as3j.messenger.dto.EditUserDto;
import com.as3j.messenger.events.RegistrationEvent;
import com.as3j.messenger.exceptions.NoSuchFileException;
import com.as3j.messenger.exceptions.NoSuchUserException;
import com.as3j.messenger.exceptions.UserWithSuchEmailExistException;
import com.as3j.messenger.model.entities.User;
import com.as3j.messenger.services.FileService;
import com.as3j.messenger.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;
    private final FileService fileService;
    private final MyPasswordEncoder encoder;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public UserController(UserService userService, FileService fileService, MyPasswordEncoder encoder,
                          ApplicationEventPublisher applicationEventPublisher) {
        this.userService = userService;
        this.fileService = fileService;
        this.encoder = encoder;
        this.applicationEventPublisher = applicationEventPublisher;
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

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public void registerUser(@RequestBody @Valid AddUserDto addUserDto) throws UserWithSuchEmailExistException {
        User user = userService.create(convertToUser(addUserDto));
        applicationEventPublisher.publishEvent(new RegistrationEvent(this, user));
    }

    private User convertToUser(AddUserDto addUserDto) {
        User user = new User();
        user.setEmail(addUserDto.getEmail());
        user.setUsername(addUserDto.getUsername());
        user.setPassword(encoder.encode(addUserDto.getPassword()));
        return user;
    }
}
