package com.as3j.messenger.services.impl;

import com.as3j.messenger.model.entities.User;
import com.as3j.messenger.exceptions.AttemptToBlacklistYourselfException;
import com.as3j.messenger.exceptions.NoSuchUserException;
import com.as3j.messenger.exceptions.UserAlreadyBlacklistedException;
import com.as3j.messenger.exceptions.UserNotBlacklistedException;
import com.as3j.messenger.repositories.UserRepository;
import com.as3j.messenger.services.BlackListService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class BlackListServiceImpl implements BlackListService {

    private final UserRepository userRepository;

    public BlackListServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @PreAuthorize("authenticated")
    public Set<User> getBlackList() throws NoSuchUserException {
        User current = getCurrentUser();
        return current.getBlackList();
    }

    @Override
    public void addToBlackList(UUID userID) throws NoSuchUserException, UserAlreadyBlacklistedException, AttemptToBlacklistYourselfException {
        User currentUser = getCurrentUser();
        User blackListedUser = userRepository.findById(userID).orElseThrow(NoSuchUserException::new);
        if (blackListedUser.equals(currentUser))
            throw new AttemptToBlacklistYourselfException();
        if (currentUser.getBlackList().contains(blackListedUser)){
            throw new UserAlreadyBlacklistedException();
        }
        currentUser.getBlackList().add(blackListedUser);
        userRepository.save(currentUser);
    }

    @Override
    public void removeFromBlackList(UUID userID) throws UserNotBlacklistedException, NoSuchUserException {
        User currentUser = getCurrentUser();
        User blackListedUser = userRepository.findById(userID).orElseThrow(NoSuchUserException::new);
        if (!currentUser.getBlackList().contains(blackListedUser))
            throw new UserNotBlacklistedException();
        currentUser.getBlackList().remove(blackListedUser);
        userRepository.save(currentUser);
    }

    private User getCurrentUser() throws NoSuchUserException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName;
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            currentUserName = authentication.getName();
        } else {
            throw new NoSuchUserException();
        }
        return userRepository.findByEmail(currentUserName).orElseThrow(NoSuchUserException::new);
    }
}
