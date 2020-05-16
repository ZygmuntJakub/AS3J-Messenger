package com.as3j.messenger.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ChatMustHaveAtLeastTwoMembersException extends Exception {

    public ChatMustHaveAtLeastTwoMembersException(String message) {
        super(message);
    }
}
