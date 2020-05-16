package com.as3j.messenger.dto;

import java.util.Set;

public class AddChatDto {

    private final String name;
    private final Set<String> users;

    public AddChatDto(String name, Set<String> users) {
        this.name = name;
        this.users = users;
    }

    public String getName() {
        return name;
    }

    public Set<String> getUsers() {
        return users;
    }
}
