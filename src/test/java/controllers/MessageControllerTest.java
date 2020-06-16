package controllers;

import com.as3j.messenger.controllers.MessageController;
import com.as3j.messenger.dto.SingleValueDto;
import com.as3j.messenger.exceptions.MessageAuthorIsNotMemberOfChatException;
import com.as3j.messenger.exceptions.NoSuchChatException;
import com.as3j.messenger.exceptions.NoSuchUserException;
import com.as3j.messenger.model.entities.User;
import com.as3j.messenger.profanity_filter.ProfanityFilterService;
import com.as3j.messenger.services.DetectLanguageService;
import com.as3j.messenger.services.MessageService;
import com.as3j.messenger.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;
import java.util.Locale;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class MessageControllerTest {

    private MessageController messageController;
    private MessageService messageService;
    private UserService userService;
    private SimpMessagingTemplate webSocket;
    private DetectLanguageService detectLanguageService;
    private ProfanityFilterService profanityFilterService;

    private UserDetails userDetails;

    private User author;

    private UUID chatUuid;
    private SingleValueDto<String> content;

    @BeforeEach
    void setUp() {
        messageService = mock(MessageService.class);
        userService = mock(UserService.class);
        userDetails = mock(UserDetails.class);
        webSocket = mock(SimpMessagingTemplate.class);
        detectLanguageService = mock(DetectLanguageService.class);
        profanityFilterService = mock(ProfanityFilterService.class);
        messageController = new MessageController(messageService, userService, webSocket, profanityFilterService,
                detectLanguageService);

        author = new User("email@mail.com");
        chatUuid = UUID.randomUUID();
        content = new SingleValueDto<>("Hi");
    }

    @Test
    void shouldAddMessage() throws NoSuchUserException, MessageAuthorIsNotMemberOfChatException, NoSuchChatException, IOException {
        // given
        doReturn(author.getEmail()).when(userDetails).getUsername();
        doReturn(author).when(userService).getByEmail(any(String.class));
        doReturn("pl").when(detectLanguageService).detect(anyString());
        doReturn(content.getValue()).when(profanityFilterService).filterCurseWords(anyString(), anyString());
        // when
        messageController.sendMessage(UUID.randomUUID(), userDetails, content);
        // then
        verify(userService, times(1)).getByEmail(any(String.class));
        verify(messageService, times(1)).sendMessage(any(UUID.class), any(User.class), any(String.class));
    }

    @Test
    void shouldThrowExceptionWhenUserIsNotFound() throws NoSuchUserException {
        // given
        doReturn(author.getEmail()).when(userDetails).getUsername();
        doThrow(NoSuchUserException.class).when(userService).getByEmail(any(String.class));
        // then
        assertThrows(NoSuchUserException.class, () -> messageController.sendMessage(chatUuid, userDetails, content));
    }

    @Test
    void shouldThrowExceptionWhenChatIsNotFound() throws NoSuchChatException, NoSuchUserException, MessageAuthorIsNotMemberOfChatException, IOException {
        // given
        doReturn(author.getEmail()).when(userDetails).getUsername();
        doReturn(author).when(userService).getByEmail(any(String.class));
        doReturn("pl").when(detectLanguageService).detect(anyString());
        doReturn(content.getValue()).when(profanityFilterService).filterCurseWords(anyString(), anyString());
        doThrow(NoSuchChatException.class).when(messageService).sendMessage(any(UUID.class), any(User.class), any(String.class));
        // then
        assertThrows(NoSuchChatException.class, () -> messageController.sendMessage(chatUuid, userDetails, content));
    }
}
