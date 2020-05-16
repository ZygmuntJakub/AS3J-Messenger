package services;

import com.as3j.messenger.dto.AddChatDto;
import com.as3j.messenger.entities.Chat;
import com.as3j.messenger.exceptions.ChatMustHaveAtLeastTwoMembersException;
import com.as3j.messenger.repositories.ChatRepository;
import com.as3j.messenger.repositories.UserRepository;
import com.as3j.messenger.services.impl.ChatServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ChatServiceImplTest {

    private ChatRepository chatRepository;
    private ChatServiceImpl chatService;

    @BeforeEach
    void setUp() {
        chatRepository = mock(ChatRepository.class);
        UserRepository userRepository = mock(UserRepository.class);
        chatService = new ChatServiceImpl(chatRepository, userRepository);
    }

    @Test
    void shouldAddChat() throws ChatMustHaveAtLeastTwoMembersException {
        // given
        Set<String> users = new HashSet<>(Arrays.asList("someone", "someone-else"));
        AddChatDto chat = new AddChatDto("sampleChat", users);
        // when
        chatService.add(chat);
        // then
        verify(chatRepository, times(1)).save(any(Chat.class));
    }

    @Test
    void shouldThrowExceptionWhenThereIsOnlyMember() {
        // given
        Set<String> users = new HashSet<>(Collections.singletonList("onlyuser"));
        AddChatDto chat = new AddChatDto("sampleChat", users);
        // then
        assertThrows(ChatMustHaveAtLeastTwoMembersException.class, () -> chatService.add(chat));
    }

    @Test
    void shouldThrowExceptionWhenThereIsNoMembers() {
        // given
        AddChatDto emptyChat = new AddChatDto("sampleChat", new HashSet<>());
        // then
        assertThrows(ChatMustHaveAtLeastTwoMembersException.class, () -> chatService.add(emptyChat));
    }
}
