package com.as3j.messenger.services;

import com.as3j.messenger.dto.AddChatDto;
import com.as3j.messenger.dto.ChatDto;
import com.as3j.messenger.exceptions.NoSuchUserException;
import com.as3j.messenger.model.entities.User;

import java.util.List;

public interface ChatService {

    void add(AddChatDto chat) throws NoSuchUserException;

    List<ChatDto> getAll(User user) throws NoSuchUserException;
}
