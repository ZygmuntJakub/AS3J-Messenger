package com.as3j.messenger.services.impl;

import com.as3j.messenger.dto.AddChatDto;
import com.as3j.messenger.entities.Chat;
import com.as3j.messenger.entities.User;
import com.as3j.messenger.exceptions.ChatMustHaveAtLeastTwoMembersException;
import com.as3j.messenger.exceptions.ChatWithSuchNameAlreadyExistsException;
import com.as3j.messenger.exceptions.NoSuchUserException;
import com.as3j.messenger.repositories.ChatRepository;
import com.as3j.messenger.repositories.UserRepository;
import com.as3j.messenger.services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;
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
    public void add(AddChatDto dto) throws ChatMustHaveAtLeastTwoMembersException, NoSuchUserException,
                                           ChatWithSuchNameAlreadyExistsException {
        if (dto.getUsersUuid().size() < 2) {
            throw new ChatMustHaveAtLeastTwoMembersException();
        }

        Set<User> users = dto.getUsersUuid().stream()
                .map(uuid -> userRepository.findByUuid(UUID.fromString(uuid)))
                .collect(Collectors.toSet());

        if (users.contains(null)) {
            throw new NoSuchUserException();
        }

        if (chatRepository.findByName(dto.getName()) != null) {
            throw new ChatWithSuchNameAlreadyExistsException();
        }

        Chat chat = new Chat();
        chat.setName(dto.getName());
        chat.setUsers(users);

        chatRepository.save(chat);
    }
}