package com.as3j.messenger.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;

public class ChatDto {

    @NotNull
    @Size(min = 1, max = 50)
    private final String name;

    @NotNull
    private final UUID chatUuid;

    @NotNull
    private final String lastMessage;

    @NotNull
    private final LocalDateTime timestamp;


    public ChatDto(@NotNull @Size(min = 1, max = 50) String name, @NotNull UUID chatUuid, @NotNull String lastMessage, LocalDateTime timestamp) {
        this.name = name;
        this.chatUuid = chatUuid;
        this.lastMessage = lastMessage;
        this.timestamp = timestamp;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getName() {
        return name;
    }

    public UUID getChatUuid() {
        return chatUuid;
    }

    public String getLastMessage() {
        return lastMessage;
    }
}
