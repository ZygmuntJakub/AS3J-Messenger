package services;

import com.as3j.messenger.dto.SendMessageDto;
import com.as3j.messenger.exceptions.MessageAuthorIsNotMemberOfChatException;
import com.as3j.messenger.exceptions.NoSuchChatException;
import com.as3j.messenger.model.entities.Chat;
import com.as3j.messenger.model.entities.Message;
import com.as3j.messenger.model.entities.User;
import com.as3j.messenger.repositories.ChatRepository;
import com.as3j.messenger.repositories.MessageRepository;
import com.as3j.messenger.services.impl.MessageServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class MessageServiceImplTest {

    private MessageServiceImpl sut;

    private MessageRepository messageRepository;
    private ChatRepository chatRepository;

    private User author;
    private Chat chat;
    private Set<User> chatMembers;
    private SendMessageDto message;

    @BeforeEach
    void setUp() {
        messageRepository = mock(MessageRepository.class);
        chatRepository = mock(ChatRepository.class);
        sut = new MessageServiceImpl(messageRepository, chatRepository);

        author = new User("email@mail.com");
        chat = new Chat();
        chatMembers = new HashSet<>(Arrays.asList(author, new User("anotheruser1@mail.com"),
                new User("anotheruser2@mail.com")));
        chat.setUsers(chatMembers);
        message = new SendMessageDto(UUID.randomUUID(), "Hi");
    }

    @Test
    void shouldAddMessage() throws MessageAuthorIsNotMemberOfChatException, NoSuchChatException {
        // given
        doReturn(Optional.of(chat)).when(chatRepository).findById(any(UUID.class));
        // when
        sut.sendMessage(message, author);
        // then
        verify(messageRepository, times(1)).save(any(Message.class));
        verify(chatRepository, times(1)).findById(any(UUID.class));
    }

    @Test
    void shouldThrowExceptionWhenChatIsNotFound() {
        // given
        doReturn(Optional.empty()).when(chatRepository).findById(any(UUID.class));
        // then
        assertThrows(NoSuchChatException.class, () -> sut.sendMessage(message, author));
    }

    @Test
    void shouldThrowExceptionWhenAuthorIsNotMemberOfChat() {
        // given
        chatMembers.remove(author);
        doReturn(Optional.of(chat)).when(chatRepository).findById(any(UUID.class));
        // then
        assertThrows(MessageAuthorIsNotMemberOfChatException.class, () -> sut.sendMessage(message, author));
    }
}
