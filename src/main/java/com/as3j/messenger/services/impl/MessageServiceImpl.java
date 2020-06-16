package com.as3j.messenger.services.impl;

import com.as3j.messenger.dto.MessageDto;
import com.as3j.messenger.exceptions.MessageAuthorIsNotMemberOfChatException;
import com.as3j.messenger.exceptions.NoSuchChatException;
import com.as3j.messenger.exceptions.NoSuchMessageException;
import com.as3j.messenger.exceptions.UserIsNotMemberOfChatException;
import com.as3j.messenger.model.entities.Chat;
import com.as3j.messenger.model.entities.Message;
import com.as3j.messenger.model.entities.User;
import com.as3j.messenger.repositories.ChatRepository;
import com.as3j.messenger.repositories.MessageRepository;
import com.as3j.messenger.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

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
    public MessageDto sendMessage(UUID chatUuid, User author, String content) throws NoSuchChatException,
            MessageAuthorIsNotMemberOfChatException {
        Chat chat = chatRepository.findById(chatUuid)
                .orElseThrow(NoSuchChatException::new);

        if (!chat.getUsers().contains(author)) {
            throw new MessageAuthorIsNotMemberOfChatException();
        }

        Message message = new Message();
        message.setChat(chat);
        message.setUser(author);
        message.setContent(content);

        messageRepository.save(message);
        return new MessageDto(message);
    }

    @Override
    public MessageDto getMessage(UUID chatUuid, User user, Long id) throws NoSuchMessageException, UserIsNotMemberOfChatException {
        Message message = messageRepository
                .findById(id)
                .orElseThrow(NoSuchMessageException::new);
        if(!message.getChat().getUuid().equals(chatUuid)) {
            throw new NoSuchMessageException();
        }
        if(!message.getChat().getUsers().contains(user)) {
            throw new UserIsNotMemberOfChatException();
        }
        return new MessageDto(message);
    }
}
