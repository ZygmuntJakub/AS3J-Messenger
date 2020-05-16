package com.as3j.messenger.entities;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    @Column(nullable = false, length = 30, unique = true)
    private String username;

    @NotNull
    @Column(nullable = false)
    private String avatarUrl;

    @ManyToMany
    @JoinTable(
            name = "user_blacklist",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "blacklist_user_id")}
    )
    private Set<User> blackList = new HashSet<>();

    public User() {
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
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
