package com.as3j.messenger.controllers;

import com.as3j.messenger.dto.UserDto;
import com.as3j.messenger.exceptions.*;
import com.as3j.messenger.model.entities.User;
import com.as3j.messenger.services.BlackListService;
import com.as3j.messenger.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("blacklists")
public class BlackListController {

    private final BlackListService blackListService;
    private final UserService userService;

    @Autowired
    public BlackListController(BlackListService blackListService, UserService userService) {
        this.blackListService = blackListService;
        this.userService = userService;
    }

    @GetMapping
    public Set<UserDto> getBlackList(@AuthenticationPrincipal UserDetails userDetails) throws NoSuchUserException {
        User user = userService.getByEmail(userDetails.getUsername());
        return user.getBlackList().stream().map(UserDto::fromUserEntity).collect(Collectors.toSet());
    }

    @PostMapping("{id}")
    public void addToBlackList(@PathVariable("id") UUID userId, @AuthenticationPrincipal UserDetails userDetails)
            throws NoSuchUserException, UserAlreadyBlacklistedException, AttemptToBlacklistYourselfException {
        User user = userService.getByEmail(userDetails.getUsername());
        blackListService.addToBlackList(userId, user);
    }

    @DeleteMapping("{id}")
    public void removeFromBlackList(@PathVariable("id") UUID userId, @AuthenticationPrincipal UserDetails userDetails)
            throws NoSuchUserException, UserNotBlacklistedException {
        User user = userService.getByEmail(userDetails.getUsername());
        blackListService.removeFromBlackList(userId, user);
    }
}