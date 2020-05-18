package com.as3j.messenger.dto;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

public class MessageDto {

    @NotNull
    private final String content;

    @NotNull
    private final String author;

    private final String avatar;

    @NotNull
    private final LocalDateTime timestamp;

    public MessageDto(@NotNull String content, @NotNull String author, String avatar,  @NotNull LocalDateTime timestamp) {
        this.content = content;
        this.author = author;
        this.avatar = avatar;
        this.timestamp = timestamp;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getAvatar() {
        return avatar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MessageDto)) return false;

        MessageDto that = (MessageDto) o;

        if (!getAuthor().equals(that.getAuthor())) return false;
        if (!getTimestamp().equals(that.getTimestamp())) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = getAuthor().hashCode();
        result = 31 * result + getTimestamp().hashCode();
        return result;
    }
}
