package services;

import com.as3j.messenger.dto.SendMessageDto;
import com.as3j.messenger.exceptions.NoSuchChatException;
import com.as3j.messenger.exceptions.NoSuchUserException;
import com.as3j.messenger.model.entities.Chat;
import com.as3j.messenger.model.entities.Message;
import com.as3j.messenger.model.entities.User;
import com.as3j.messenger.repositories.ChatRepository;
import com.as3j.messenger.repositories.MessageRepository;
import com.as3j.messenger.repositories.UserRepository;
import com.as3j.messenger.services.impl.MessageServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class MessageServiceImplTest {

    private MessageServiceImpl sut;

    private MessageRepository messageRepository;
    private UserRepository userRepository;
    private ChatRepository chatRepository;

    private HttpServletRequest httpServletRequest;
    private Principal testPrincipal;

    private User testUser;
    private Chat testChat;
    private SendMessageDto testMessage;

    @BeforeEach
    void setUp() {
        messageRepository = mock(MessageRepository.class);
        userRepository = mock(UserRepository.class);
        chatRepository = mock(ChatRepository.class);
        httpServletRequest = mock(HttpServletRequest.class);
        testPrincipal = () -> "someone";
        sut = new MessageServiceImpl(messageRepository, userRepository, chatRepository, httpServletRequest);

        testUser = new User("email@mail.com");
        testChat = new Chat();
        testMessage = new SendMessageDto(UUID.randomUUID(), "Hi!");
    }

    @Test
    void shouldAddMessage() throws NoSuchUserException, NoSuchChatException {
        // given
        doReturn(testPrincipal).when(httpServletRequest).getUserPrincipal();
        doReturn(Optional.of(testUser)).when(userRepository).findByEmail(any(String.class));
        doReturn(Optional.of(testChat)).when(chatRepository).findByUuid(any(UUID.class));
        // when
        sut.sendMessage(testMessage);
        // then
        verify(messageRepository, times(1)).save(any(Message.class));
        verify(userRepository, times(1)).findByEmail(any(String.class));
        verify(chatRepository, times(1)).findByUuid(any(UUID.class));
    }

    @Test
    void shouldThrowExceptionWhenAuthorIsNotFound() {
        // given
        doReturn(Optional.empty()).when(userRepository).findByEmail(any(String.class));
        doReturn(testPrincipal).when(httpServletRequest).getUserPrincipal();
        // then expect exception
        assertThrows(NoSuchUserException.class, () -> sut.sendMessage(testMessage));
    }

    @Test
    void shouldThrowExceptionWhenChatIsNotFound() {
        // given
        doReturn(testPrincipal).when(httpServletRequest).getUserPrincipal();
        doReturn(Optional.of(testUser)).when(userRepository).findByEmail(any(String.class));
        doReturn(Optional.empty()).when(chatRepository).findByUuid(any(UUID.class));
        // then expect exception
        assertThrows(NoSuchChatException.class, () -> sut.sendMessage(testMessage));
    }
}
