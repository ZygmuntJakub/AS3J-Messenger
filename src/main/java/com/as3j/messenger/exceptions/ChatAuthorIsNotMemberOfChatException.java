package com.as3j.messenger.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Author must be a member of chat")
public class ChatAuthorIsNotMemberOfChatException extends Exception {
}