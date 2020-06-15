package com.as3j.messenger.services.impl;

import com.as3j.messenger.dto.ChangePasswordDto;
import com.as3j.messenger.exceptions.NoSuchUserException;
import com.as3j.messenger.exceptions.UserWithSuchEmailExistException;
import com.as3j.messenger.exceptions.WrongCurrentPasswordException;
import com.as3j.messenger.model.entities.User;
import com.as3j.messenger.repositories.UserRepository;
import com.as3j.messenger.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User update(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    @Override
    public User getById(UUID id) throws NoSuchUserException {
        return userRepository.findById(id).orElseThrow(NoSuchUserException::new);
    }

    @Override
    public User getByEmail(String email) throws NoSuchUserException {
        return userRepository.findByEmail(email).orElseThrow(NoSuchUserException::new);
    }

    @Override
    public User create(User user) throws UserWithSuchEmailExistException {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new UserWithSuchEmailExistException();
        }

        return userRepository.save(user);
    }

    @Override
    public void changePassword(User user, ChangePasswordDto changePasswordDto) throws WrongCurrentPasswordException {
        if (passwordEncoder.matches(changePasswordDto.getCurrentPassword(), user.getPassword()))
            user.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
        else
            throw new WrongCurrentPasswordException();
        userRepository.save(user);
    }
}
