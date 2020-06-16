package com.as3j.messenger.controllers;

import com.as3j.messenger.common.LanguageCodes;
import com.as3j.messenger.dto.MessageDto;
import com.as3j.messenger.dto.SingleValueDto;
import com.as3j.messenger.exceptions.*;
import com.as3j.messenger.model.entities.User;
import com.as3j.messenger.services.MessageService;
import com.as3j.messenger.services.TranslationService;
import com.as3j.messenger.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.gax.rpc.InvalidArgumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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
    private final TranslationService translationService;
    private final SimpMessagingTemplate webSocket;

    @Autowired
    public MessageController(MessageService messageService, UserService userService,
                             TranslationService translationService, SimpMessagingTemplate webSocket) {
        this.messageService = messageService;
        this.userService = userService;
        this.translationService = translationService;
        this.webSocket = webSocket;
    }

    @PostMapping(consumes = "application/json")
    public void sendMessage(@PathVariable("id") UUID chatUuid,
                            @AuthenticationPrincipal UserDetails userDetails,
                            @RequestBody @Valid SingleValueDto<String> content) throws NoSuchUserException, NoSuchChatException,
            MessageAuthorIsNotMemberOfChatException {
        User author = userService.getByEmail(userDetails.getUsername());
        MessageDto sentMessage = messageService.sendMessage(chatUuid, author, content.getValue());

        String destination = "/messages/add/" + chatUuid.toString();
        String payload = null;
        try {
            payload = new ObjectMapper().writeValueAsString(sentMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        webSocket.convertAndSend(destination, payload);
    }

    @GetMapping(path = "/{messageId}")
    public MessageDto getTranslatedMessage(@PathVariable("id") UUID chatUuid,
                                           @AuthenticationPrincipal UserDetails userDetails,
                                           @PathVariable("messageId") Long messageId,
                                           @RequestParam("lang") String language)
            throws NoSuchUserException, NoSuchMessageException, UserIsNotMemberOfChatException,
            IncorrectLanguageCode, LanguageNotSupportedException {
        User requester = userService.getByEmail(userDetails.getUsername());
        MessageDto messageDto = messageService.getMessage(chatUuid, requester, messageId);
        if(!LanguageCodes.isValid(language)) {
            throw new IncorrectLanguageCode();
        }
        try {
            String translated = translationService.translate(messageDto.getContent(), language);
            messageDto.setContent(translated);
        } catch(InvalidArgumentException e) {
            throw new LanguageNotSupportedException();
        }
        return messageDto;
    }
}
