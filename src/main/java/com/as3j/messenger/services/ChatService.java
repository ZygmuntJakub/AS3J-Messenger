package com.as3j.messenger.services;

import com.as3j.messenger.dto.AddChatDto;
import com.as3j.messenger.exceptions.ChatMustHaveAtLeastTwoMembersException;
import com.as3j.messenger.exceptions.ChatWithSuchNameAlreadyExistsException;
import com.as3j.messenger.exceptions.NoSuchUserException;

public interface ChatService {

    void add(AddChatDto chat) throws ChatMustHaveAtLeastTwoMembersException, NoSuchUserException,
                                     ChatWithSuchNameAlreadyExistsException;
}
