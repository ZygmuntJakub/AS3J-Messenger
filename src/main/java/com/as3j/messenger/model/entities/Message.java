package com.as3j.messenger.model.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "chat_id", insertable = false, updatable = false)
    private Chat chat;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime timestamp;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    @Lob
    @Column(nullable = false)
    private String content;

    public Message() {
    }

    public Message(Chat chat, @NotNull LocalDateTime timestamp, @NotNull User user, @NotNull String content) {
        this.chat = chat;
        this.timestamp = timestamp;
        this.user = user;
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message)) return false;

        Message message = (Message) o;

        if (!getChat().equals(message.getChat())) return false;
        if (!getTimestamp().equals(message.getTimestamp())) return false;
        return getUser().equals(message.getUser());
    }

    @Override
    public int hashCode() {
        int result = getChat().hashCode();
        result = 31 * result + getTimestamp().hashCode();
        result = 31 * result + getUser().hashCode();
        return result;
    }
}
