package com.as3j.messenger.services;

import com.as3j.messenger.dto.AddChatDto;
import com.as3j.messenger.exceptions.ChatMustHaveAtLeastTwoMembersException;

public interface ChatService {

    void add(AddChatDto chat) throws ChatMustHaveAtLeastTwoMembersException;
}
