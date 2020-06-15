package com.as3j.messenger;

import com.as3j.messenger.model.entities.Chat;
import com.as3j.messenger.model.entities.Message;
import com.as3j.messenger.model.entities.User;
import com.as3j.messenger.repositories.ChatRepository;
import com.as3j.messenger.repositories.MessageRepository;
import com.as3j.messenger.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;

@Component
public class DatabaseInit implements ApplicationRunner {

    private UserRepository userRepository;
    private MessageRepository messageRepository;
    private ChatRepository chatRepository;

    @Autowired
    public DatabaseInit(UserRepository userRepository, MessageRepository messageRepository, ChatRepository chatRepository) {
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
        this.chatRepository = chatRepository;
    }

    public void run(ApplicationArguments args) {
//        User user = new User();
//        user.setAvatarPresent(false);
//        user.setUsername("user1");
//        user.setEmail("user1@mail.com");
//        user.setPassword("$2y$10$eZGjS7qvh8V0ynyyh6FU9ecW/FY.Tq4IT3Dq4icOZFNfSmRmeHp52");
//        user.setBlackList(Collections.emptySet());
//        userRepository.save(user);
//
//        User user2 = new User();
//        user2.setAvatarPresent(false);
//        user2.setUsername("user2");
//        user2.setEmail("user2@mail.com");
//        user2.setPassword("$2y$10$eZGjS7qvh8V0ynyyh6FU9ecW/FY.Tq4IT3Dq4icOZFNfSmRmeHp52");
//        user2.setBlackList(Collections.emptySet());
//        userRepository.save(user2);
//
//        User user3 = new User();
//        user3.setAvatarPresent(false);
//        user3.setUsername("user3");
//        user3.setEmail("user3@mail.com");
//        user3.setPassword("$2y$10$eZGjS7qvh8V0ynyyh6FU9ecW/FY.Tq4IT3Dq4icOZFNfSmRmeHp52");
//        user3.setBlackList(Collections.emptySet());
//        userRepository.save(user3);
//
//        User user4 = new User();
//        user4.setAvatarPresent(false);
//        user4.setUsername("user4");
//        user4.setEmail("user4@mail.com");
//        user4.setPassword("$2y$10$eZGjS7qvh8V0ynyyh6FU9ecW/FY.Tq4IT3Dq4icOZFNfSmRmeHp52");
//        user4.setBlackList(Set.of(user2, user3));
//        userRepository.save(user4);
//
//        Chat chat = new Chat();
//
//        Message message = new Message();
//        message.setChat(chat);
//        message.setTimestamp();
//        message.setUser(user);
//        message.setContent("Hi");
//
//        Message message2 = new Message();
//        message2.setChat(chat);
//        message2.setTimestamp();
//        message2.setUser(user2);
//        message2.setContent("Hi, bro");
//
//        chat.setUsers(Set.of(user, user2));
//        chat.setName("Chat 1");
//        chat.setMessages(Set.of(message, message2));
//
//        chatRepository.save(chat);
    }
}
