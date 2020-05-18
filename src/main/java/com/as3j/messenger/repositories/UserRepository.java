package com.as3j.messenger.repositories;

import com.as3j.messenger.model.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends CrudRepository<User, UUID> {

    Optional<User> findByEmail(String email);
}
