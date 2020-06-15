package com.as3j.messenger.services;

import com.as3j.messenger.dto.MessageDto;
import com.as3j.messenger.exceptions.MessageAuthorIsNotMemberOfChatException;
import com.as3j.messenger.exceptions.NoSuchChatException;
import com.as3j.messenger.exceptions.NoSuchUserException;
import com.as3j.messenger.model.entities.User;

import java.util.UUID;

public interface MessageService {

    MessageDto sendMessage(UUID chatUuid, User author, String content) throws NoSuchUserException, NoSuchChatException,
            MessageAuthorIsNotMemberOfChatException;
}
