package com.as3j.messenger.services.impl;

import com.as3j.messenger.dto.AddChatDto;
import com.as3j.messenger.exceptions.NoSuchUserException;
import com.as3j.messenger.model.entities.Chat;
import com.as3j.messenger.model.entities.User;
import com.as3j.messenger.repositories.ChatRepository;
import com.as3j.messenger.repositories.UserRepository;
import com.as3j.messenger.services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
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
    public void add(AddChatDto dto) throws NoSuchUserException {
        Set<Optional<User>> users = dto.getUsersUuid().stream()
                    .map(userRepository::findById)
                    .collect(Collectors.toSet());

        if (users.stream().anyMatch(Optional::isEmpty)) {
            throw new NoSuchUserException();
        }

        Chat chat = new Chat();
        chat.setName(dto.getName());
        chat.setUsers(users.stream().
                map(Optional::get).
                collect(Collectors.toSet()));

        chatRepository.save(chat);
    }
}