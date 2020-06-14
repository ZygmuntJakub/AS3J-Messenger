package com.as3j.messenger.events;

import com.as3j.messenger.model.entities.User;
import org.springframework.context.ApplicationEvent;

public class RegistrationEvent extends ApplicationEvent {
    private final User user;

    public RegistrationEvent(Object source, User newUser) {
        super(source);
        this.user = newUser;
    }

    public User getUser() {
        return user;
    }
}
