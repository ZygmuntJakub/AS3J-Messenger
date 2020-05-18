package com.as3j.messenger.controllers;

import com.as3j.messenger.authentication.UserDetailsImpl;
import com.as3j.messenger.dto.AddChatDto;
import com.as3j.messenger.dto.ChatDto;
import com.as3j.messenger.dto.MessageDto;
import com.as3j.messenger.exceptions.ChatAuthorIsNotMemberOfChatException;
import com.as3j.messenger.exceptions.MessageAuthorIsNotMemberOfChatException;
import com.as3j.messenger.exceptions.NoSuchChatException;
import com.as3j.messenger.exceptions.NoSuchUserException;
import com.as3j.messenger.model.entities.User;
import com.as3j.messenger.services.ChatService;
import com.as3j.messenger.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("chats")
public class ChatController {

    private final ChatService chatService;

    private final UserService userService;

    @Autowired
    public ChatController(ChatService chatService, UserService userService) {
        this.chatService = chatService;
        this.userService = userService;
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addChat(@RequestBody @Valid AddChatDto chat, @AuthenticationPrincipal UserDetails userDetails)
            throws NoSuchUserException, ChatAuthorIsNotMemberOfChatException {
        chatService.add(chat, userDetails.getUsername());
    }

    @GetMapping
    @ResponseBody
    public List<ChatDto> getChats(@AuthenticationPrincipal UserDetails userDetails) throws NoSuchUserException {
        User user = userService.getByEmail(userDetails.getUsername());
        return chatService.getAll(user);
    }

    @GetMapping("{id}")
    @ResponseBody
    public List<MessageDto> getChat(@PathVariable UUID id, @AuthenticationPrincipal UserDetails userDetails)
            throws NoSuchUserException, MessageAuthorIsNotMemberOfChatException, NoSuchChatException {
        User user = userService.getByEmail(userDetails.getUsername());
        return chatService.get(user, id);
    }
}