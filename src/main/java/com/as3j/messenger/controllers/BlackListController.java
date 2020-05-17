package com.as3j.messenger.controllers;

import com.as3j.messenger.dto.BlackListUserDto;
import com.as3j.messenger.exceptions.AttemptToBlacklistYourselfException;
import com.as3j.messenger.exceptions.NoSuchUserException;
import com.as3j.messenger.exceptions.UserAlreadyBlacklistedException;
import com.as3j.messenger.exceptions.UserNotBlacklistedException;
import com.as3j.messenger.services.BlackListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("blacklist")
public class BlackListController {

    private final BlackListService blackListService;

    @Autowired
    public BlackListController(BlackListService blackListService) {
        this.blackListService = blackListService;
    }

    @GetMapping()
    public Set<BlackListUserDto> getBlackList() throws NoSuchUserException {
        return blackListService.getBlackList().stream().map(BlackListUserDto::fromUserEntity)
                .collect(Collectors.toSet());
    }

    @PostMapping(value="add")
    public void addToBlackList(@RequestParam("user") @NotNull UUID userId) throws NoSuchUserException, UserAlreadyBlacklistedException, AttemptToBlacklistYourselfException {
        blackListService.addToBlackList(userId);
    }

    @PostMapping(value="remove")
    public void removeFromBlackList(@RequestParam("user") @NotNull UUID userId) throws NoSuchUserException, UserNotBlacklistedException {
        blackListService.removeFromBlackList(userId);
    }
}