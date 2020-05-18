package com.as3j.messenger.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

public class SendMessageDto {

    @NotNull
    private final UUID chatUuid;

    @NotBlank
    private final String content;

    public SendMessageDto(@NotNull UUID chatUuid,
                          @NotBlank String content) {
        this.chatUuid = chatUuid;
        this.content = content;
    }

    public UUID getChatUuid() {
        return chatUuid;
    }

    public String getContent() {
        return content;
    }
}
