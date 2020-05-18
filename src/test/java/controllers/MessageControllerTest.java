package controllers;

import com.as3j.messenger.controllers.MessageController;
import com.as3j.messenger.dto.SendMessageDto;
import com.as3j.messenger.exceptions.MessageAuthorIsNotMemberOfChatException;
import com.as3j.messenger.exceptions.NoSuchChatException;
import com.as3j.messenger.exceptions.NoSuchUserException;
import com.as3j.messenger.services.MessageService;
import com.as3j.messenger.services.impl.MessageServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class MessageControllerTest {

    private MessageController sut;
    private MessageService messageService;

    private SendMessageDto testMessage;

    @BeforeEach
    void setUp() {
        messageService = mock(MessageService.class);
        sut = new MessageController(messageService);

        testMessage = testMessage = new SendMessageDto(UUID.randomUUID(), "Hi!");
    }

    @Test
    void shouldAddMessage() throws NoSuchUserException, NoSuchChatException, MessageAuthorIsNotMemberOfChatException {
        // when
        sut.sendMessage(testMessage);
        // then
        verify(messageService, times(1)).sendMessage(any(SendMessageDto.class));
    }

    @Test
    void shouldThrowExceptionWhenUserIsNotFound() throws NoSuchUserException, NoSuchChatException,
            MessageAuthorIsNotMemberOfChatException {
        // given
        doThrow(NoSuchUserException.class).when(messageService).sendMessage(any(SendMessageDto.class));
        // then
        assertThrows(NoSuchUserException.class, () -> sut.sendMessage(testMessage));
    }

    @Test
    void shouldThrowExceptionWhenChatIsNotFound() throws NoSuchUserException, NoSuchChatException,
            MessageAuthorIsNotMemberOfChatException {
        // given
        doThrow(NoSuchChatException.class).when(messageService).sendMessage(any(SendMessageDto.class));
        // then
        assertThrows(NoSuchChatException.class, () -> sut.sendMessage(testMessage));
    }
}
