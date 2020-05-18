package com.as3j.messenger.services.impl;

import com.as3j.messenger.exceptions.NoSuchUserException;
import com.as3j.messenger.model.entities.User;
import com.as3j.messenger.repositories.UserRepository;
import com.as3j.messenger.services.UserService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void update(User user) {
        userRepository.save(user);
    }

    @Override
    public User getById(UUID id) throws NoSuchUserException {
        return userRepository.findById(id).orElseThrow(NoSuchUserException::new);
    }

    @Override
    public User getByEmail(String email) throws NoSuchUserException {
        return userRepository.findByEmail(email).orElseThrow(NoSuchUserException::new);
    }
}
