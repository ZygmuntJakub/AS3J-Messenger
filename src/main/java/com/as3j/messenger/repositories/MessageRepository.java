package com.as3j.messenger.repositories;

import com.as3j.messenger.model.entities.Message;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, Long> {
}
