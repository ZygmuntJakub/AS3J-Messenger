package com.as3j.messenger.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

public class MessageBundle {
    private MessageSource messageSource;

    @Autowired
    public MessageBundle(MessageSource messageSource) {
        this.messageSource = messageSource;
    }


}
