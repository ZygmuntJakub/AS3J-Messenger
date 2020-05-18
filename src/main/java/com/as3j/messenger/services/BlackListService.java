package com.as3j.messenger.services;

import com.as3j.messenger.exceptions.*;
import com.as3j.messenger.model.entities.User;

import java.util.UUID;

public interface BlackListService {

    void addToBlackList(UUID userID, User currentUser) throws NoSuchUserException, UserAlreadyBlacklistedException,
            AttemptToBlacklistYourselfException;

    void removeFromBlackList(UUID userID, User currentUser) throws UserNotBlacklistedException, NoSuchUserException;

}
