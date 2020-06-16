package controllers;

import com.as3j.messenger.controllers.MessageController;
import com.as3j.messenger.dto.MessageDto;
import com.as3j.messenger.dto.SingleValueDto;
import com.as3j.messenger.exceptions.*;
import com.as3j.messenger.model.entities.User;
import com.as3j.messenger.curse_filter.CurseFilter;
import com.as3j.messenger.services.MessageService;
import com.as3j.messenger.services.TranslationService;
import com.as3j.messenger.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.io.IOException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class MessageControllerTest {

    private MessageController messageController;
    private MessageService messageService;
    private TranslationService translationService;
    private UserService userService;
    private SimpMessagingTemplate webSocket;
    private CurseFilter curseFilter;

    private UserDetails userDetails;

    private User author;
    private MessageDto message;

    private UUID chatUuid;
    private SingleValueDto<String> content;

    @BeforeEach
    void setUp() {
        messageService = mock(MessageService.class);
        userService = mock(UserService.class);
        userDetails = mock(UserDetails.class);
        webSocket = mock(SimpMessagingTemplate.class);
        translationService = mock(TranslationService.class);
        curseFilter = mock(CurseFilter.class);
        messageController = new MessageController(
                messageService,
                userService,
                webSocket,
                curseFilter,
                translationService);

        author = new User("email@mail.com");
        chatUuid = UUID.randomUUID();
        content = new SingleValueDto<>("Hi");
        message = new MessageDto("Test", "Test", null, LocalDateTime.now());
    }

    @Test
    void shouldAddMessage() throws NoSuchUserException, MessageAuthorIsNotMemberOfChatException, NoSuchChatException, IOException {
        // given
        doReturn(author.getEmail()).when(userDetails).getUsername();
        doReturn(author).when(userService).getByEmail(any(String.class));
        doReturn("pl").when(translationService).detect(anyString());
        doReturn(content.getValue()).when(curseFilter).filterCurseWords(anyString(), anyString());
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
        doReturn("pl").when(translationService).detect(anyString());
        doReturn(content.getValue()).when(curseFilter).filterCurseWords(anyString(), anyString());
        doThrow(NoSuchChatException.class).when(messageService).sendMessage(any(UUID.class), any(User.class), any(String.class));
        // then
        assertThrows(NoSuchChatException.class, () -> messageController.sendMessage(chatUuid, userDetails, content));
    }

    @Test
    void shouldTranslateMessage() throws NoSuchUserException, NoSuchMessageException, UserIsNotMemberOfChatException,
            LanguageNotSupportedException, IncorrectLanguageCode {
        // given
        doReturn(author.getEmail()).when(userDetails).getUsername();
        doReturn(author).when(userService).getByEmail(any(String.class));
        doReturn(message).when(messageService).getMessage(any(UUID.class), any(User.class), eq(1L));
        var originalString = message.getContent();
        var expectedString = "Translated text";
        doReturn(expectedString).when(translationService).translate(any(String.class), eq("en"));

        // when
        var dto = messageController.getTranslatedMessage(UUID.randomUUID(), userDetails, 1L, "en");
        // then
        verify(userService, times(1)).getByEmail(any(String.class));
        verify(messageService, times(1)).getMessage(any(UUID.class), any(User.class), eq(1L));
        verify(translationService, times(1)).translate(eq(originalString), eq("en"));
        assertEquals(expectedString, dto.getContent());
    }
}
