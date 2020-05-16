package com.as3j.messenger.services.impl;

import com.as3j.messenger.dto.AddChatDto;
import com.as3j.messenger.entities.Chat;
import com.as3j.messenger.exceptions.ChatMustHaveAtLeastTwoMembersException;
import com.as3j.messenger.repositories.ChatRepository;
import com.as3j.messenger.repositories.UserRepository;
import com.as3j.messenger.services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final UserRepository userRepository;

    @Autowired
    public ChatServiceImpl(ChatRepository chatRepository, UserRepository userRepository) {
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void add(AddChatDto dto) throws ChatMustHaveAtLeastTwoMembersException {
        if (dto.getUsers().size() < 2) {
            throw new ChatMustHaveAtLeastTwoMembersException("Chat must have at least two members");
        }

        Chat chat = new Chat();
        chat.setName(dto.getName());
        chat.setUsers(dto.getUsers().stream().map(userRepository::findByUsername).collect(Collectors.toSet()));

        chatRepository.save(chat);
    }
}