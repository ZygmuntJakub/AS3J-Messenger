package controllers;

import com.as3j.messenger.authentication.UserDetailsImpl;
import com.as3j.messenger.controllers.ChatController;
import com.as3j.messenger.dto.AddChatDto;
import com.as3j.messenger.dto.ChatDto;
import com.as3j.messenger.exceptions.NoSuchUserException;
import com.as3j.messenger.model.entities.Chat;
import com.as3j.messenger.model.entities.Message;
import com.as3j.messenger.model.entities.User;
import com.as3j.messenger.services.ChatService;
import com.as3j.messenger.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ChatControllerTest {

    private ChatService chatService;
    private ChatController chatController;
    private UserService userService;
    private UserDetailsImpl userDetails;

    @BeforeEach
    void setUp() {
        chatService = mock(ChatService.class);
        userService = mock(UserService.class);
        chatController = new ChatController(chatService, userService);
        userDetails = new UserDetailsImpl("", "");
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
    void shouldFindChats() throws NoSuchUserException {
        // given
        User user = new User("email@example.com");
        doReturn(user).when(userService).getByEmail(any(String.class));
        ChatDto chatDto = new ChatDto("name", UUID.randomUUID(), "hi", LocalDateTime.now().minusHours(1));
        ChatDto chatDto2 = new ChatDto("name", UUID.randomUUID(), "hello", LocalDateTime.now());
        doReturn(Arrays.asList(chatDto, chatDto2)).when(chatService).getAll(any(User.class));
        // when
        chatController.getChats(userDetails);
        // then
        verify(chatService, times(1)).getAll(any(User.class));
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