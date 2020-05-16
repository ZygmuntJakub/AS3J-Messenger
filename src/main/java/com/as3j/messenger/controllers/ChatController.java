package com.as3j.messenger.controllers;

import com.as3j.messenger.dto.AddChatDto;
import com.as3j.messenger.exceptions.ChatMustHaveAtLeastTwoMembersException;
import com.as3j.messenger.exceptions.NoSuchUserException;
import com.as3j.messenger.services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("chat")
public class ChatController {

    private final ChatService chatService;

    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping(consumes = "application/json")
    @RequestMapping("add")
    public void addChat(@RequestBody AddChatDto chat) throws ChatMustHaveAtLeastTwoMembersException, NoSuchUserException {
        chatService.add(chat);
    }
}
