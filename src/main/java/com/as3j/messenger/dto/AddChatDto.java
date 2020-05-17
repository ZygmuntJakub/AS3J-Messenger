package com.as3j.messenger.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

public class AddChatDto {

    @NotNull
    @Size(min = 1, max = 50)
    private final String name;

    @NotEmpty
    private final Set<@Size(min = 36, max = 36) String> usersUuid;

    public AddChatDto(String name, Set<String> usersUuid) {
        this.name = name;
        this.usersUuid = usersUuid;
    }

    public String getName() {
        return name;
    }

    public Set<String> getUsersUuid() {
        return usersUuid;
    }
}
