package com.as3j.messenger.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Chat with such name already exists")
public class ChatWithSuchNameAlreadyExistsException extends Exception {
}
