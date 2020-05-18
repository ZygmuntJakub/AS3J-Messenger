package com.as3j.messenger.services.impl;

import com.as3j.messenger.exceptions.*;
import com.as3j.messenger.model.entities.User;
import com.as3j.messenger.repositories.UserRepository;
import com.as3j.messenger.services.BlackListService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BlackListServiceImpl implements BlackListService {

    private final UserRepository userRepository;

    public BlackListServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void addToBlackList(UUID userID, User currentUser) throws NoSuchUserException,
            UserAlreadyBlacklistedException, AttemptToBlacklistYourselfException {
        User blackListedUser = userRepository.findById(userID).orElseThrow(NoSuchUserException::new);
        if (blackListedUser.equals(currentUser))
            throw new AttemptToBlacklistYourselfException();
        if (currentUser.getBlackList().contains(blackListedUser)) {
            throw new UserAlreadyBlacklistedException();
        }
        currentUser.getBlackList().add(blackListedUser);
        userRepository.save(currentUser);
    }

    @Override
    public void removeFromBlackList(UUID userID, User currentUser) throws UserNotBlacklistedException,
            NoSuchUserException {
        User blackListedUser = userRepository.findById(userID).orElseThrow(NoSuchUserException::new);
        if (!currentUser.getBlackList().contains(blackListedUser))
            throw new UserNotBlacklistedException();
        currentUser.getBlackList().remove(blackListedUser);
        userRepository.save(currentUser);
    }
}
