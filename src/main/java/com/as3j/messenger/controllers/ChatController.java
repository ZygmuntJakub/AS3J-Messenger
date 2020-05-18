package com.as3j.messenger.controllers;

import com.as3j.messenger.dto.AddChatDto;
import com.as3j.messenger.exceptions.NoSuchUserException;
import com.as3j.messenger.services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("chats")
public class ChatController {

    private final ChatService chatService;

    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addChat(@RequestBody @Valid AddChatDto chat) throws NoSuchUserException {
        chatService.add(chat);
    }
}