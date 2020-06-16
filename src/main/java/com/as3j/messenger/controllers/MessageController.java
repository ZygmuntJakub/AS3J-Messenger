package com.as3j.messenger.controllers;

import com.as3j.messenger.dto.MessageDto;
import com.as3j.messenger.dto.SingleValueDto;
import com.as3j.messenger.exceptions.MessageAuthorIsNotMemberOfChatException;
import com.as3j.messenger.exceptions.NoSuchChatException;
import com.as3j.messenger.exceptions.NoSuchUserException;
import com.as3j.messenger.model.entities.User;
import com.as3j.messenger.profanity_filter.ProfanityFilterService;
import com.as3j.messenger.services.MessageService;
import com.as3j.messenger.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Locale;
import java.util.UUID;

@RestController
@RequestMapping("chats/{id}/messages")
public class MessageController {

    private final MessageService messageService;
    private final UserService userService;
    private final SimpMessagingTemplate webSocket;
    @Autowired
    private ProfanityFilterService profanityFilterService;

    @Autowired
    public MessageController(MessageService messageService, UserService userService, SimpMessagingTemplate webSocket) {
        this.messageService = messageService;
        this.userService = userService;
        this.webSocket = webSocket;
    }

    @PostMapping(consumes = "application/json")
    public void sendMessage(@PathVariable("id") UUID chatUuid,
                            @AuthenticationPrincipal UserDetails userDetails,
                            @RequestBody @Valid SingleValueDto<String> content,
                            Locale locale) throws NoSuchUserException, NoSuchChatException,
            MessageAuthorIsNotMemberOfChatException, IOException {
        User author = userService.getByEmail(userDetails.getUsername());
        String message = profanityFilterService.filterCurseWords(content.getValue(), locale.getLanguage());
        MessageDto sentMessage = messageService.sendMessage(chatUuid, author, message);

        String destination = "/messages/add/" + chatUuid.toString();
        String payload = null;
        try {
            payload = new ObjectMapper().writeValueAsString(sentMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        webSocket.convertAndSend(destination, payload);
    }
}
