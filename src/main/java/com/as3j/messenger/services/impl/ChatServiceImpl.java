package com.as3j.messenger.services.impl;

import com.as3j.messenger.dto.AddChatDto;
import com.as3j.messenger.dto.ChatDto;
import com.as3j.messenger.dto.MessageDto;
import com.as3j.messenger.exceptions.ChatAuthorIsNotMemberOfChatException;
import com.as3j.messenger.exceptions.MessageAuthorIsNotMemberOfChatException;
import com.as3j.messenger.exceptions.NoSuchChatException;
import com.as3j.messenger.exceptions.NoSuchUserException;
import com.as3j.messenger.model.entities.Chat;
import com.as3j.messenger.model.entities.Message;
import com.as3j.messenger.model.entities.User;
import com.as3j.messenger.repositories.ChatRepository;
import com.as3j.messenger.repositories.MessageRepository;
import com.as3j.messenger.repositories.UserRepository;
import com.as3j.messenger.services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
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
    public void add(AddChatDto dto, String email) throws NoSuchUserException, ChatAuthorIsNotMemberOfChatException {

        Set<Optional<User>> users = dto.getUsersUuid().stream()
                .map(userRepository::findById)
                .collect(Collectors.toSet());

        if (users.stream().anyMatch(Optional::isEmpty)) {
            throw new NoSuchUserException();
        }

        Chat chat = new Chat();
        String chatName = dto.getName();

        Set<User> userSet = users.stream()
                .map(Optional::get)
                .collect(Collectors.toSet());
        User author = userSet.stream()
                .filter(u -> u.getEmail().equals(email))
                .findAny().orElseThrow(ChatAuthorIsNotMemberOfChatException::new);
        Message message = new Message(chat, LocalDateTime.now(), author, String.format("Chat %s created by %s", chatName, author.getUsername()));

        chat.setName(chatName);
        chat.setUsers(userSet);
        chat.getMessages().add(message);

        chatRepository.save(chat);
    }

    @Override
    public List<ChatDto> getAll(User user) {
        List<Chat> chats = chatRepository.findAllByUsersContains(user);
        return chats.stream().map(c -> {
            Message lastMessage = c.getMessages().stream()
                    .max(Comparator.comparing(Message::getTimestamp))
                    .get();
            return new ChatDto(c.getName(), c.getUuid(), lastMessage.getContent(), lastMessage.getTimestamp());
        }).sorted(Comparator.comparing(ChatDto::getTimestamp)).collect(Collectors.toList());

    }

    @Override
    public List<MessageDto> get(User user, UUID id) throws NoSuchChatException, MessageAuthorIsNotMemberOfChatException {
        Chat chat = chatRepository.findById(id).orElseThrow(NoSuchChatException::new);
        if (!chat.getUsers().contains(user)) throw new MessageAuthorIsNotMemberOfChatException();
        return chat.getMessages().stream()
                .map(c -> new MessageDto(c.getContent(), c.getUser().getUsername(),
                        c.getUser().getAvatarPresent() ? c.getUser().getUuid().toString() : null,
                        c.getTimestamp()))
                .sorted(Comparator.comparing(MessageDto::getTimestamp))
                .collect(Collectors.toList());

    }
}