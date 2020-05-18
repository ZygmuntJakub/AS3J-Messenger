package services;

import com.as3j.messenger.dto.AddChatDto;
import com.as3j.messenger.exceptions.NoSuchUserException;
import com.as3j.messenger.model.entities.Chat;
import com.as3j.messenger.model.entities.Message;
import com.as3j.messenger.model.entities.User;
import com.as3j.messenger.repositories.ChatRepository;
import com.as3j.messenger.repositories.UserRepository;
import com.as3j.messenger.services.impl.ChatServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
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
    void shouldAddChat() throws NoSuchUserException {
        // given
        Set<UUID> users = new HashSet<>(Arrays.asList(UUID.randomUUID(), UUID.randomUUID()));
        doReturn(Optional.of(new User("email@example.com"))).when(userRepository).findById(any(UUID.class));
        AddChatDto chat = new AddChatDto("sampleChat", users);
        // when
        chatService.add(chat);
        // then
        verify(chatRepository, times(1)).save(any(Chat.class));
    }

    @Test
    void shouldFindChats() throws NoSuchUserException {
        // given
        User user = new User("email@example.com");
        User user2 = new User("email2@example.com");
        Set<User> users = new HashSet<>(Arrays.asList(user, user2));
        Chat chat = new Chat("hi", users, new HashSet<>());
        Message message = new Message(chat, LocalDateTime.now().minusHours(1), user,"hi");
        Message message2 = new Message(chat, LocalDateTime.now(), user2,"hello");
        chat.getMessages().add(message);
        chat.getMessages().add(message2);
        List<Chat> chats = new ArrayList<>(Arrays.asList(chat));
        doReturn(Optional.of(new User("email@example.com"))).when(userRepository).findById(any(UUID.class));
        doReturn(chats).when(chatRepository).findAllByUsersContains(any(User.class));
        // when
        chatService.getAll(user);
        // then
        verify(chatRepository, times(1)).findAllByUsersContains(any(User.class));
    }

    @Test
    void shouldThrowExceptionWhenUserIsNotFound() {
        // given
        Set<UUID> users = new HashSet<>(Arrays.asList(UUID.randomUUID(), UUID.randomUUID()));
        doReturn(Optional.ofNullable(null)).when(userRepository).findById(any(UUID.class));
        AddChatDto chat = new AddChatDto("sampleChat", users);
        // then
        assertThrows(NoSuchUserException.class, () -> chatService.add(chat));
    }
}
