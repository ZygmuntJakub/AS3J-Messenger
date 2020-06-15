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
import com.as3j.messenger.repositories.UserRepository;
import com.as3j.messenger.services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
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
    public void add(AddChatDto dto, String email) throws NoSuchUserException, ChatAuthorIsNotMemberOfChatException {

        Set<User> users = dto.getUsersUuid().stream()
                .map(userRepository::findById)
                .map(u -> u.orElse(null))
                .collect(Collectors.toSet());

        if (users.contains(null)) throw new NoSuchUserException();

        Chat chat = new Chat();
        String chatName = dto.getName();

        User author = users.stream()
                .filter(u -> u.getEmail().equals(email))
                .findAny().orElseThrow(ChatAuthorIsNotMemberOfChatException::new);
        Message message = new Message(chat, author, String.format("Chat %s created by %s", chatName, author.getUsername()));

        chat.setName(chatName);
        chat.setUsers(users);
        chat.getMessages().add(message);

        chatRepository.save(chat);
    }

    @Override
    public List<ChatDto> getAll(User user) {
        List<Chat> chats = chatRepository.findAllByUsersContains(user);
        return chats.stream()
                .map(c -> c.getMessages().stream()
                        .max(Comparator.comparing(Message::getTimestamp)).get())
                .map(m -> new ChatDto(m.getChat().getName(), m.getChat().getUuid(), m.getContent(), m.getTimestamp()))
                .sorted(Comparator.comparing(ChatDto::getTimestamp)).collect(Collectors.toList());
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