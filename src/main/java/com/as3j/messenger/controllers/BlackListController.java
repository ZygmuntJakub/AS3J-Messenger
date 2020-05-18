package com.as3j.messenger.controllers;

import com.as3j.messenger.dto.BlackListUserDto;
import com.as3j.messenger.exceptions.*;
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
    public Set<BlackListUserDto> getBlackList() throws NoSuchUserException, UnauthorizedUserException {
        return blackListService.getBlackList().stream().map(BlackListUserDto::fromUserEntity)
                .collect(Collectors.toSet());
    }

    @PostMapping(value="add")
    public void addToBlackList(@RequestParam("user") @NotNull UUID userId) throws NoSuchUserException, UserAlreadyBlacklistedException, AttemptToBlacklistYourselfException, UnauthorizedUserException {
        blackListService.addToBlackList(userId);
    }

    @PostMapping(value="remove")
    public void removeFromBlackList(@RequestParam("user") @NotNull UUID userId) throws NoSuchUserException, UserNotBlacklistedException, UnauthorizedUserException {
        blackListService.removeFromBlackList(userId);
    }
}