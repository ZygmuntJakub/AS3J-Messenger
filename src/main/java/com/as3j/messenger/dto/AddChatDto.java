package com.as3j.messenger.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;
import java.util.UUID;

public class AddChatDto {

    @NotNull
    @Size(min = 1, max = 50)
    private final String name;

    @NotEmpty
    @Size(min = 2)
    private final Set<UUID> usersUuid;

    public AddChatDto(String name, Set<UUID> usersUuid) {
        this.name = name;
        this.usersUuid = usersUuid;
    }

    public String getName() {
        return name;
    }

    public Set<UUID> getUsersUuid() {
        return usersUuid;
    }
}
