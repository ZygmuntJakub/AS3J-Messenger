package com.as3j.messenger.services;

import com.as3j.messenger.exceptions.*;
import com.as3j.messenger.model.entities.User;

import java.util.Set;
import java.util.UUID;

public interface BlackListService {
    Set<User> getBlackList() throws NoSuchUserException, UnauthorizedUserException;
    void addToBlackList(UUID userID) throws NoSuchUserException, UserAlreadyBlacklistedException, AttemptToBlacklistYourselfException, UnauthorizedUserException;
    void removeFromBlackList(UUID userID) throws UserNotBlacklistedException, NoSuchUserException, UnauthorizedUserException;

}
