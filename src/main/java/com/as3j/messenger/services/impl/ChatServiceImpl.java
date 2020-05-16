package com.as3j.messenger.services.impl;

import com.as3j.messenger.entities.Chat;
import com.as3j.messenger.repositories.ChatRepository;
import com.as3j.messenger.services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;

public class ChatServiceImpl implements ChatService {

    private ChatRepository chatRepository;

    @Autowired
    public ChatServiceImpl(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public void add(Chat chat) {
    }
}
