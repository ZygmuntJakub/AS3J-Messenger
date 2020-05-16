package com.as3j.messenger.repositories;

import com.as3j.messenger.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
