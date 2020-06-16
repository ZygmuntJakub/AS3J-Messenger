package com.as3j.messenger.services;

import com.as3j.messenger.dto.MessageDto;
import com.as3j.messenger.exceptions.*;
import com.as3j.messenger.model.entities.Message;
import com.as3j.messenger.model.entities.User;

import java.util.UUID;

public interface MessageService {

    MessageDto sendMessage(UUID chatUuid, User author, String content) throws NoSuchUserException, NoSuchChatException,
            MessageAuthorIsNotMemberOfChatException;
    MessageDto getMessage(UUID chatUuid, User user, Long id) throws NoSuchMessageException, UserIsNotMemberOfChatException;
}
