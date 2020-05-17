package services;

import com.as3j.messenger.dto.AddChatDto;
import com.as3j.messenger.entities.Chat;
import com.as3j.messenger.entities.User;
import com.as3j.messenger.exceptions.ChatMustHaveAtLeastTwoMembersException;
import com.as3j.messenger.exceptions.ChatWithSuchNameAlreadyExistsException;
import com.as3j.messenger.exceptions.NoSuchUserException;
import com.as3j.messenger.repositories.ChatRepository;
import com.as3j.messenger.repositories.UserRepository;
import com.as3j.messenger.services.impl.ChatServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ChatServiceImplTest {

    private ChatRepository chatRepository;
    private UserRepository userRepository;
    private ChatServiceImpl chatService;

    @BeforeEach
    void setUp() {
        chatRepository = mock(ChatRepository.class);
        userRepository = mock(UserRepository.class);
        chatService = new ChatServiceImpl(chatRepository, userRepository);
    }

    @Test
    void shouldAddChat() throws ChatMustHaveAtLeastTwoMembersException, NoSuchUserException, ChatWithSuchNameAlreadyExistsException {
        // given
        Set<String> users = new HashSet<>(Arrays.asList(UUID.randomUUID().toString(), UUID.randomUUID().toString()));
        doReturn(new User("email@example.com")).when(userRepository).findByUuid(any(UUID.class));
        AddChatDto chat = new AddChatDto("sampleChat", users);
        // when
        chatService.add(chat);
        // then
        verify(chatRepository, times(1)).save(any(Chat.class));
    }

    @Test
    void shouldThrowExceptionWhenThereIsOnlyOneMember() {
        // given
        Set<String> users = new HashSet<>(Collections.singletonList(UUID.randomUUID().toString()));
        AddChatDto chat = new AddChatDto("sampleChat", users);
        // then
        assertThrows(ChatMustHaveAtLeastTwoMembersException.class, () -> chatService.add(chat));
    }

    @Test
    void shouldThrowExceptionWhenThereAreNoMembers() {
        // given
        AddChatDto emptyChat = new AddChatDto("sampleChat", new HashSet<>());
        // then
        assertThrows(ChatMustHaveAtLeastTwoMembersException.class, () -> chatService.add(emptyChat));
    }

    @Test
    void shouldThrowExceptionWhenUserIsNotFound() {
        // given
        Set<String> users = new HashSet<>(Arrays.asList(UUID.randomUUID().toString(), UUID.randomUUID().toString()));
        doReturn(null).when(userRepository).findByUuid(any(UUID.class));
        AddChatDto chat = new AddChatDto("sampleChat", users);
        // then
        assertThrows(NoSuchUserException.class, () -> chatService.add(chat));
    }

    @Test
    void shouldThrowExceptionWhenChatWithSuchNameAlreadyExists() {
        // given
        Set<String> users = new HashSet<>(Arrays.asList(UUID.randomUUID().toString(), UUID.randomUUID().toString()));
        doReturn(new User("email@example.com")).when(userRepository).findByUuid(any(UUID.class));
        doReturn(new Chat()).when(chatRepository).findByName(any(String.class));
        AddChatDto chat = new AddChatDto("sampleChat", users);
        // then
        assertThrows(ChatWithSuchNameAlreadyExistsException.class, () -> chatService.add(chat));
    }
}
