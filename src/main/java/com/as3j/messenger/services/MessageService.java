package com.as3j.messenger.services;

import com.as3j.messenger.dto.SendMessageDto;
import com.as3j.messenger.exceptions.MessageAuthorIsNotMemberOfChatException;
import com.as3j.messenger.exceptions.NoSuchChatException;
import com.as3j.messenger.exceptions.NoSuchUserException;
import com.as3j.messenger.model.entities.User;

import javax.servlet.http.HttpServletRequest;

public interface MessageService {

    void sendMessage(SendMessageDto dto, User author) throws NoSuchUserException, NoSuchChatException,
            MessageAuthorIsNotMemberOfChatException;
}
