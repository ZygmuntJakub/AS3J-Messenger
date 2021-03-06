package com.as3j.messenger.model.entities;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type = "uuid-char")
    private UUID uuid;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(nullable = false, length = 50)
    private String name;

    @ManyToMany
    @JoinTable(
            name = "chat_users",
            joinColumns = {@JoinColumn(name = "chat_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    @NotEmpty
    @Size(min = 2)
    private Set<User> users = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "chat_id")
    private Set<Message> messages = new HashSet<>();

    public Chat() {
    }

    public Chat(UUID uuid) {
        this.uuid = uuid;
    }

    public Chat(@NotNull @Size(min = 1, max = 50) String name, @NotEmpty @Size(min = 2) Set<User> users, Set<Message> messages) {
        this.name = name;
        this.users = users;
        this.messages = messages;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Chat)) return false;

        Chat chat = (Chat) o;

        return name.equals(chat.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
