package com.as3j.messenger.services.impl;

import com.as3j.messenger.dto.SendMessageDto;
import com.as3j.messenger.exceptions.MessageAuthorIsNotMemberOfChatException;
import com.as3j.messenger.exceptions.NoSuchChatException;
import com.as3j.messenger.exceptions.NoSuchUserException;
import com.as3j.messenger.model.entities.Chat;
import com.as3j.messenger.model.entities.Message;
import com.as3j.messenger.model.entities.User;
import com.as3j.messenger.repositories.ChatRepository;
import com.as3j.messenger.repositories.MessageRepository;
import com.as3j.messenger.repositories.UserRepository;
import com.as3j.messenger.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final ChatRepository chatRepository;
    private final HttpServletRequest request;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository, UserRepository userRepository,
                              ChatRepository chatRepository, HttpServletRequest request) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.chatRepository = chatRepository;
        this.request = request;
    }

    @Override
    public void sendMessage(SendMessageDto messageDto) throws NoSuchUserException, NoSuchChatException,
            MessageAuthorIsNotMemberOfChatException {
        User author = userRepository.findByEmail(request.getUserPrincipal().getName())
                .orElseThrow(NoSuchUserException::new);

        Chat chat = chatRepository.findByUuid(messageDto.getChatUuid())
                .orElseThrow(NoSuchChatException::new);

        if (chat.getUsers().stream().noneMatch(user -> user.equals(author))) {
            throw new MessageAuthorIsNotMemberOfChatException();
        }

        Message message = new Message();
        message.setChat(chat);
        message.setUser(author);
        message.setContent(messageDto.getContent());

        messageRepository.save(message);
    }
}
