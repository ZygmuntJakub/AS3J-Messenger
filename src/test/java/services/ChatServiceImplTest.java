package services;

import com.as3j.messenger.entities.Chat;
import com.as3j.messenger.entities.User;
import com.as3j.messenger.exceptions.ChatMustHaveAtLeastTwoUsersException;
import com.as3j.messenger.repositories.ChatRepository;
import com.as3j.messenger.services.impl.ChatServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ChatServiceImplTest {

    private ChatRepository chatRepository;
    private ChatServiceImpl chatService;

    @BeforeEach
    private void setUp() {
        chatRepository = mock(ChatRepository.class);
        chatService = new ChatServiceImpl(chatRepository);
    }

    @Test
    private void shouldAddChat() {
        // given
        User firstUser = new User("email@example.com", "P@ssw0rd", "someone", "http://example.com");
        User secondUser = new User("anotheremail@example.com", "Pa$$w0rd", "someone-else", "http://example2.com");
        Set<User> users = new HashSet<>(Arrays.asList(firstUser, secondUser));
        Chat chat = new Chat("sampleChat", users);
        // when
        chatService.add(chat);
        // then
        verify(chatRepository, times(1)).save(any(Chat.class));
    }

    @Test
    private void shouldThrowExceptionWhenThereIsOnlyUser() {
        // given
        User onlyUser = new User("email@example.com", "P@ssw0rd", "someone", "http://example.com");
        Set<User> users = new HashSet<>(Collections.singletonList(onlyUser));
        Chat chat = new Chat("sampleChat", users);
        // then
        assertThrows(ChatMustHaveAtLeastTwoUsersException.class, () -> chatService.add(chat));
    }

    @Test
    private void shouldThrowExceptionWhenThereIsNoUsers() {
        // given
        Chat emptyChat = new Chat("sampleChat", new HashSet<>());
        // then
        assertThrows(ChatMustHaveAtLeastTwoUsersException.class, () -> chatService.add(emptyChat));
    }
}
