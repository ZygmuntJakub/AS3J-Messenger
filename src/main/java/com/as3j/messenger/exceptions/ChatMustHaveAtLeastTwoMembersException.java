package com.as3j.messenger.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Chat must have at least two members")
public class ChatMustHaveAtLeastTwoMembersException extends Exception {
}
