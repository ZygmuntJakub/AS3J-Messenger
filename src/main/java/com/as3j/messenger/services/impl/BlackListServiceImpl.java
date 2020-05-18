package com.as3j.messenger.services.impl;

import com.as3j.messenger.exceptions.*;
import com.as3j.messenger.model.entities.User;
import com.as3j.messenger.repositories.UserRepository;
import com.as3j.messenger.services.BlackListService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

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
    public Set<User> getBlackList() throws NoSuchUserException, UnauthorizedUserException {
        User current = getCurrentUser();
        return current.getBlackList();
    }

    @Override
    public void addToBlackList(UUID userID) throws NoSuchUserException, UserAlreadyBlacklistedException, AttemptToBlacklistYourselfException, UnauthorizedUserException {
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
    public void removeFromBlackList(UUID userID) throws UserNotBlacklistedException, NoSuchUserException, UnauthorizedUserException {
        User currentUser = getCurrentUser();
        User blackListedUser = userRepository.findById(userID).orElseThrow(NoSuchUserException::new);
        if (!currentUser.getBlackList().contains(blackListedUser))
            throw new UserNotBlacklistedException();
        currentUser.getBlackList().remove(blackListedUser);
        userRepository.save(currentUser);
    }

    private User getCurrentUser() throws NoSuchUserException, UnauthorizedUserException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName;
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            currentUserName = authentication.getName();
            return userRepository.findByEmail(currentUserName).orElseThrow(NoSuchUserException::new);
        } else{
            throw new UnauthorizedUserException();
        }

    }
}
