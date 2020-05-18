package com.as3j.messenger.services.impl;

import com.as3j.messenger.dto.SendMessageDto;
import com.as3j.messenger.exceptions.MessageAuthorIsNotMemberOfChatException;
import com.as3j.messenger.exceptions.NoSuchChatException;
import com.as3j.messenger.model.entities.Chat;
import com.as3j.messenger.model.entities.Message;
import com.as3j.messenger.model.entities.User;
import com.as3j.messenger.repositories.ChatRepository;
import com.as3j.messenger.repositories.MessageRepository;
import com.as3j.messenger.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository, ChatRepository chatRepository) {
        this.messageRepository = messageRepository;
        this.chatRepository = chatRepository;
    }

    @Override
    public void sendMessage(SendMessageDto messageDto, User author) throws NoSuchChatException,
            MessageAuthorIsNotMemberOfChatException {
        Chat chat = chatRepository.findById(messageDto.getChatUuid())
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
