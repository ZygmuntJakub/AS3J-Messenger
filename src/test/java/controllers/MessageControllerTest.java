package controllers;

import com.as3j.messenger.controllers.MessageController;
import com.as3j.messenger.dto.SendMessageDto;
import com.as3j.messenger.exceptions.MessageAuthorIsNotMemberOfChatException;
import com.as3j.messenger.exceptions.NoSuchChatException;
import com.as3j.messenger.exceptions.NoSuchUserException;
import com.as3j.messenger.model.entities.User;
import com.as3j.messenger.services.MessageService;
import com.as3j.messenger.services.UserService;
import com.as3j.messenger.services.impl.MessageServiceImpl;
import com.as3j.messenger.services.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class MessageControllerTest {

    private MessageController sut;

    private MessageService messageService;
    private UserService userService;
    private HttpServletRequest request;

    private User author;
    private SendMessageDto message;

    @BeforeEach
    void setUp() {
        messageService = mock(MessageServiceImpl.class);
        userService = mock(UserServiceImpl.class);
        request = mock(MockHttpServletRequest.class);
        sut = new MessageController(messageService, userService, request);

        author = new User("email@mail.com");
        message = new SendMessageDto(UUID.randomUUID(), "Hi");
    }

    private Principal getTestPrincipal() {
        return () -> author.getEmail();
    }

    @Test
    void shouldAddMessage() throws NoSuchUserException, MessageAuthorIsNotMemberOfChatException, NoSuchChatException {
        // given
        doReturn(getTestPrincipal()).when(request).getUserPrincipal();
        doReturn(author).when(userService).getByEmail(any(String.class));
        // when
        sut.sendMessage(message);
        // then
        verify(userService, times(1)).getByEmail(any(String.class));
        verify(messageService, times(1)).sendMessage(any(SendMessageDto.class), any(User.class));
    }

    @Test
    void shouldThrowExceptionWhenUserIsNotFound() throws NoSuchUserException {
        // given
        doReturn(getTestPrincipal()).when(request).getUserPrincipal();
        doThrow(NoSuchUserException.class).when(userService).getByEmail(any(String.class));
        // then
        assertThrows(NoSuchUserException.class, () -> sut.sendMessage(message));
    }

    @Test
    void shouldThrowExceptionWhenChatIsNotFound() throws NoSuchChatException, NoSuchUserException, MessageAuthorIsNotMemberOfChatException {
        // given
        doReturn(getTestPrincipal()).when(request).getUserPrincipal();
        doReturn(author).when(userService).getByEmail(any(String.class));
        doThrow(NoSuchChatException.class).when(messageService).sendMessage(any(SendMessageDto.class), any(User.class));
        // then
        assertThrows(NoSuchChatException.class, () -> sut.sendMessage(message));
    }
}
