package controllers;

import com.as3j.messenger.controllers.ChatController;
import com.as3j.messenger.dto.AddChatDto;
import com.as3j.messenger.exceptions.NoSuchUserException;
import com.as3j.messenger.services.ChatService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ChatControllerTest {

    private ChatService chatService;
    private ChatController chatController;

    @BeforeEach
    void setUp() {
        chatService = mock(ChatService.class);
        chatController = new ChatController(chatService);
    }

    @Test
    void shouldAddChat() throws NoSuchUserException {
        // given
        Set<UUID> users = new HashSet<>(Arrays.asList(UUID.randomUUID(), UUID.randomUUID()));
        AddChatDto dto = new AddChatDto("sampleChat", users);
        // when
        chatController.addChat(dto);
        // then
        verify(chatService, times(1)).add(any(AddChatDto.class));
    }

    @Test
    void shouldThrowExceptionIfAddOperationFails() throws NoSuchUserException {
        // given
        doThrow(NoSuchUserException.class).when(chatService).add(any(AddChatDto.class));
        Set<UUID> users = new HashSet<>(Arrays.asList(UUID.randomUUID(), UUID.randomUUID()));
        AddChatDto dto = new AddChatDto("sampleChat", users);
        // then
        assertThrows(NoSuchUserException.class, () -> chatController.addChat(dto));
    }
}
