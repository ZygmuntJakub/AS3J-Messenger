package com.as3j.messenger.services;

import com.as3j.messenger.dto.ChangePasswordDto;
import com.as3j.messenger.exceptions.NoSuchUserException;
import com.as3j.messenger.exceptions.UserWithSuchEmailExistException;
import com.as3j.messenger.exceptions.WrongCurrentPasswordException;
import com.as3j.messenger.model.entities.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    User getById(UUID id) throws NoSuchUserException;

    User getByEmail(String email) throws NoSuchUserException;

    User update(User user);

    void changePassword(User user, ChangePasswordDto changePasswordDto) throws WrongCurrentPasswordException;
    User create(User user) throws UserWithSuchEmailExistException;

    List<User> getAll();
}
