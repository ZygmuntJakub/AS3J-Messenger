package com.as3j.messenger.controllers;

import com.as3j.messenger.dto.SendMessageDto;
import com.as3j.messenger.exceptions.MessageAuthorIsNotMemberOfChatException;
import com.as3j.messenger.exceptions.NoSuchChatException;
import com.as3j.messenger.exceptions.NoSuchUserException;
import com.as3j.messenger.model.entities.User;
import com.as3j.messenger.services.MessageService;
import com.as3j.messenger.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("messages")
public class MessageController {

    private final MessageService messageService;
    private final UserService userService;
    private final HttpServletRequest request;

    @Autowired
    public MessageController(MessageService messageService, UserService userService, HttpServletRequest request) {
        this.messageService = messageService;
        this.userService = userService;
        this.request = request;
    }

    @PostMapping(consumes = "application/json")
    public void sendMessage(@RequestBody @Valid SendMessageDto message) throws NoSuchUserException, NoSuchChatException,
            MessageAuthorIsNotMemberOfChatException {
        User author = userService.getByEmail(request.getUserPrincipal().getName());
        messageService.sendMessage(message, author);
    }
}
