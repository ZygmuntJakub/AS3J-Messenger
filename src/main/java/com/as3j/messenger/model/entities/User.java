package com.as3j.messenger.model.entities;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type = "uuid-char")
    private UUID uuid;

    @NotNull
    @Size(min = 3, max = 255)
    @Email
    @Column(updatable = false, nullable = false, unique = true)
    private String email;

    @Size(min = 50, max = 60)
    @Column(nullable = false, length = 60)
    private String password;

    @NotNull
    @Size(min = 3, max = 30)
    @Column(nullable = false, length = 30)
    private String username;

    @Column(name = "avatar")
    private boolean avatarPresent;

    @ManyToMany
    @JoinTable(
            name = "user_blacklist",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "blacklist_user_id")}
    )
    private Set<User> blackList = new HashSet<>();

    public User() {
    }

    public User(@NotNull @Size(min = 3, max = 255) @Email String email) {
        this.email = email;
    }

    public User(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean getAvatarPresent() {
        return avatarPresent;
    }

    public void setAvatarPresent(boolean avatarPresent) {
        this.avatarPresent = avatarPresent;
    }

    public Set<User> getBlackList() {
        return blackList;
    }

    public void setBlackList(Set<User> blackList) {
        this.blackList = blackList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        return getEmail().equals(user.getEmail());
    }

    @Override
    public int hashCode() {
        return getEmail().hashCode();
    }
}
