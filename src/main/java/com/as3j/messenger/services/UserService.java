package com.as3j.messenger.services;

import com.as3j.messenger.exceptions.NoSuchUserException;
import com.as3j.messenger.model.entities.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    User getById(UUID id) throws NoSuchUserException;

    User getByEmail(String email) throws NoSuchUserException;

    void update(User user);

    List<User> getAll();
}
