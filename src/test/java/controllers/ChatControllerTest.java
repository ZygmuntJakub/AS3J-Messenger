package controllers;

import com.as3j.messenger.controllers.ChatController;
import com.as3j.messenger.dto.AddChatDto;
import com.as3j.messenger.exceptions.ChatMustHaveAtLeastTwoMembersException;
import com.as3j.messenger.services.ChatService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ChatControllerTest {

    private ChatService chatService;
    private ChatController chatController;

    @BeforeEach
    private void setUp() {
        chatService = mock(ChatService.class);
        chatController = new ChatController(chatService);
    }

    @Test
    void shouldAddChat() throws ChatMustHaveAtLeastTwoMembersException {
        // given
        Set<String> users = new HashSet<>(Arrays.asList("someone", "someone-else"));
        AddChatDto dto = new AddChatDto("sampleChat", users);
        // when
        chatController.addChat(dto);
        // then
        verify(chatService, times(1)).add(any(AddChatDto.class));
    }

    @Test
    void shouldThrowExceptionIfAddOperationFailes() throws ChatMustHaveAtLeastTwoMembersException {
        // given
        doThrow(ChatMustHaveAtLeastTwoMembersException.class).when(chatService).add(any(AddChatDto.class));
        Set<String> users = new HashSet<>(Arrays.asList("someone", "someone-else"));
        AddChatDto dto = new AddChatDto("sampleChat", users);
        // then
        assertThrows(ChatMustHaveAtLeastTwoMembersException.class, () -> chatController.addChat(dto));
    }
}
