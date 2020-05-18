package services;

import com.as3j.messenger.dto.AddChatDto;
import com.as3j.messenger.exceptions.MessageAuthorIsNotMemberOfChatException;
import com.as3j.messenger.exceptions.NoSuchChatException;
import com.as3j.messenger.exceptions.NoSuchUserException;
import com.as3j.messenger.model.entities.Chat;
import com.as3j.messenger.model.entities.Message;
import com.as3j.messenger.model.entities.User;
import com.as3j.messenger.repositories.ChatRepository;
import com.as3j.messenger.repositories.UserRepository;
import com.as3j.messenger.services.impl.ChatServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ChatServiceImplTest {

    private ChatServiceImpl chatService;

    private ChatRepository chatRepository;
    private UserRepository userRepository;

    private User testUser;

    @BeforeEach
    void setUp() {
        chatRepository = mock(ChatRepository.class);
        userRepository = mock(UserRepository.class);
        chatService = new ChatServiceImpl(chatRepository, userRepository);

        testUser = new User("email@example.com");
    }

    @Test
    void shouldAddChat() throws NoSuchUserException {
        // given
        Set<UUID> users = new HashSet<>(Arrays.asList(UUID.randomUUID(), UUID.randomUUID()));
        doReturn(Optional.of(testUser)).when(userRepository).findById(any(UUID.class));
        AddChatDto chat = new AddChatDto("sampleChat", users);
        // when
        chatService.add(chat);
        // then
        verify(chatRepository, times(1)).save(any(Chat.class));
        verify(userRepository, times(users.size())).findById(any(UUID.class));
    }

    @Test
    void shouldFindChats() {
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
    void shouldFindChat() throws MessageAuthorIsNotMemberOfChatException, NoSuchChatException {
        // given
        User user = Mockito.mock(User.class);
        user.setEmail("email@example.com");
        user.setUsername("user");
        user.setAvatarPresent(true);

        User user2 = Mockito.mock(User.class);
        user.setEmail("email2@example.com");
        user.setUsername("user2");
        user.setAvatarPresent(false);

        Set<User> users = new HashSet<>(Arrays.asList(user, user2));
        Chat chat = new Chat("hi", users, new HashSet<>());

        Message message = new Message(chat, LocalDateTime.now().minusHours(1), user,"hi");
        Message message2 = new Message(chat, LocalDateTime.now(), user2,"hello");

        chat.getMessages().add(message);
        chat.getMessages().add(message2);

        when(user.getUuid()).thenReturn(UUID.randomUUID());
        when(user2.getUuid()).thenReturn(UUID.randomUUID());

        doReturn(Optional.of(new User("email@example.com"))).when(userRepository).findById(any(UUID.class));
        doReturn(Optional.of(chat)).when(chatRepository).findById(any(UUID.class));
        // when
        chatService.get(user, UUID.randomUUID());
        // then
        verify(chatRepository, times(1)).findById(any(UUID.class));
    }

    @Test
    void shouldThrowExceptionWhenUserIsNotFound() {
        // given
        Set<UUID> users = new HashSet<>(Arrays.asList(UUID.randomUUID(), UUID.randomUUID()));
        doReturn(Optional.empty()).when(userRepository).findById(any(UUID.class));
        AddChatDto chat = new AddChatDto("sampleChat", users);
        // then
        assertThrows(NoSuchUserException.class, () -> chatService.add(chat));
    }
}
