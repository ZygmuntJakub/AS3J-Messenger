package com.as3j.messenger.controllers;

import com.as3j.messenger.dto.SingleValueDto;
import com.as3j.messenger.exceptions.MessageAuthorIsNotMemberOfChatException;
import com.as3j.messenger.exceptions.NoSuchChatException;
import com.as3j.messenger.exceptions.NoSuchUserException;
import com.as3j.messenger.model.entities.User;
import com.as3j.messenger.services.MessageService;
import com.as3j.messenger.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("chats/{id}/messages")
public class MessageController {

    private final MessageService messageService;
    private final UserService userService;

    @Autowired
    public MessageController(MessageService messageService, UserService userService) {
        this.messageService = messageService;
        this.userService = userService;
    }

    @PostMapping(consumes = "application/json")
    public void sendMessage(@PathVariable("id") UUID chatUuid,
                            @AuthenticationPrincipal UserDetails userDetails,
                            @RequestBody @Valid SingleValueDto<String> content) throws NoSuchUserException, NoSuchChatException,
            MessageAuthorIsNotMemberOfChatException {
        User author = userService.getByEmail(userDetails.getUsername());
        messageService.sendMessage(chatUuid, author, content.getValue());
    }
}
