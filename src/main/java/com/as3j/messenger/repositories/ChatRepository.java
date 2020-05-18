package com.as3j.messenger.repositories;

import com.as3j.messenger.model.entities.Chat;
import com.as3j.messenger.model.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface ChatRepository extends CrudRepository<Chat, UUID> {

    Chat findByName(String name);

    List<Chat> findAllByUsersContains(User user);
}
