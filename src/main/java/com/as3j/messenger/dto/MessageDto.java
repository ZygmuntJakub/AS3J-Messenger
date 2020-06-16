package com.as3j.messenger.dto;

import com.as3j.messenger.model.entities.Message;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

public class MessageDto {

    private String content;

    private final String author;

    private final String avatar;

    private final Long id;

    private final LocalDateTime timestamp;

    public MessageDto(String content, String author, String avatar, LocalDateTime timestamp) {
        this.content = content;
        this.author = author;
        this.avatar = avatar;
        this.timestamp = timestamp;
        this.id = null;
    }

    public MessageDto(Message message) {
        this.content = message.getContent();
        this.author = message.getUser().getUsername();
        this.avatar = message.getUser().getAvatarPresent() ? message.getUser().getUuid().toString() : null;
        this.timestamp = message.getTimestamp();
        this.id = message.getId();
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

    public void setContent(String content) {
        this.content = content;
    }

    public Long getId() {
        return id;
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
