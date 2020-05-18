package com.as3j.messenger.services;

import com.as3j.messenger.dto.SendMessageDto;
import com.as3j.messenger.exceptions.MessageAuthorIsNotMemberOfChatException;
import com.as3j.messenger.exceptions.NoSuchChatException;
import com.as3j.messenger.exceptions.NoSuchUserException;

import javax.servlet.http.HttpServletRequest;

public interface MessageService {

    void sendMessage(SendMessageDto dto) throws NoSuchUserException, NoSuchChatException, MessageAuthorIsNotMemberOfChatException;
}
