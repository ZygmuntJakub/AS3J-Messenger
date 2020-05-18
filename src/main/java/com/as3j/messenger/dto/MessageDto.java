package com.as3j.messenger.dto;

import com.as3j.messenger.model.entities.User;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class MessageDto {

    @NotNull
    private final String content;

    @NotNull
    private final User author;

    @NotNull
    private final LocalDateTime timestamp;

    public MessageDto(@NotNull String content, @NotNull User author, @NotNull LocalDateTime timestamp) {
        this.content = content;
        this.author = author;
        this.timestamp = timestamp;
    }

    public String getContent() {
        return content;
    }

    public User getAuthor() {
        return author;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MessageDto)) return false;

        MessageDto that = (MessageDto) o;

        if (!getContent().equals(that.getContent())) return false;
        if (!getAuthor().equals(that.getAuthor())) return false;
        return getTimestamp().equals(that.getTimestamp());
    }

    @Override
    public int hashCode() {
        int result = getContent().hashCode();
        result = 31 * result + getAuthor().hashCode();
        result = 31 * result + getTimestamp().hashCode();
        return result;
    }
}
