package com.as3j.messenger.repositories;

import com.as3j.messenger.model.entities.Chat;
import org.springframework.data.repository.CrudRepository;

public interface ChatRepository extends CrudRepository<Chat, Long> {

    Chat findByName(String name);
}
