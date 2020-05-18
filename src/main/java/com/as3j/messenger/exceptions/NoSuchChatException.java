package com.as3j.messenger.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Chat not found")
public class NoSuchChatException extends Exception {
}
