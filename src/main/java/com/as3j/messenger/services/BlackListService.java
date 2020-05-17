package com.as3j.messenger.services;

import com.as3j.messenger.entities.User;
import com.as3j.messenger.exceptions.AttemptToBlacklistYourselfException;
import com.as3j.messenger.exceptions.NoSuchUserException;
import com.as3j.messenger.exceptions.UserAlreadyBlacklistedException;
import com.as3j.messenger.exceptions.UserNotBlacklistedException;

import java.util.Set;
import java.util.UUID;

public interface BlackListService {
    Set<User> getBlackList() throws NoSuchUserException;
    void addToBlackList(UUID userID) throws NoSuchUserException, UserAlreadyBlacklistedException, AttemptToBlacklistYourselfException;
    void removeFromBlackList(UUID userID) throws UserNotBlacklistedException, NoSuchUserException;

}
