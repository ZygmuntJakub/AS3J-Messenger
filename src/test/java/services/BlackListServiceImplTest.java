package services;

import com.as3j.messenger.exceptions.*;
import com.as3j.messenger.model.entities.User;
import com.as3j.messenger.repositories.UserRepository;
import com.as3j.messenger.services.BlackListService;
import com.as3j.messenger.services.impl.BlackListServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BlackListServiceImplTest {

    private UserRepository userRepository;
    private BlackListService blackListService;
    private User loggedUser;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        blackListService = new BlackListServiceImpl(userRepository);
        loggedUser = new User("test@example.com");
        loggedUser.getBlackList().addAll(Set.of(new User("test2@example.com"),
                new User("test3@example.com")));
    }

    @Test
    void shouldAddToBlackList() throws NoSuchUserException, UserAlreadyBlacklistedException,
            AttemptToBlacklistYourselfException {
        //given
        User userToBlacklist = new User("test4@example.com");
        doReturn(Optional.of(userToBlacklist)).when(userRepository).findById(any(UUID.class));
        //when
        blackListService.addToBlackList(UUID.randomUUID(), loggedUser);
        //then
        assertTrue(loggedUser.getBlackList().contains(userToBlacklist));
        verify(userRepository, times(1)).findById(any(UUID.class));
        verify(userRepository, times(1)).save(loggedUser);
    }

    @Test
    void shouldThrowExceptionWhenAddedUserIsNotFound() {
        //given
        doReturn(Optional.empty()).when(userRepository).findById(any(UUID.class));
        //then
        assertThrows(NoSuchUserException.class, () -> blackListService.addToBlackList(UUID.randomUUID(), loggedUser));
    }

    @Test
    void shouldThrowExceptionWhenUserIsAlreadyBlacklisted() {
        //given
        User userToBlackList = new User("test4@example.com");
        loggedUser.getBlackList().add(userToBlackList);
        assertTrue(loggedUser.getBlackList().contains(userToBlackList));
        doReturn(Optional.of(userToBlackList)).when(userRepository).findById(any(UUID.class));
        //then
        assertThrows(UserAlreadyBlacklistedException.class,
                () -> blackListService.addToBlackList(UUID.randomUUID(), loggedUser));
    }

    @Test
    void shouldThrowExceptionWhenUserAttemptsToBlacklistHimself() {
        //given
        doReturn(Optional.of(loggedUser)).when(userRepository).findById(any(UUID.class));
        //then
        assertThrows(AttemptToBlacklistYourselfException.class,
                () -> blackListService.addToBlackList(UUID.randomUUID(), loggedUser));
    }

    @Test
    void shouldRemoveFromBlackList() throws NoSuchUserException, UserNotBlacklistedException {
        //given
        User userToRemove = new User("test4@example.com");
        loggedUser.getBlackList().add(userToRemove);
        assertTrue(loggedUser.getBlackList().contains(userToRemove));
        doReturn(Optional.of(userToRemove)).when(userRepository).findById(any(UUID.class));
        //when
        blackListService.removeFromBlackList(UUID.randomUUID(), loggedUser);
        //verify
        assertFalse(loggedUser.getBlackList().contains(userToRemove));
        verify(userRepository, times(1)).findById(any(UUID.class));
        verify(userRepository, times(1)).save(loggedUser);
    }

    @Test
    void shouldThrowExceptionWhenUserIsNotBlacklisted() {
        //given
        User userToBlackList = new User("test4@example.com");
        assertFalse(loggedUser.getBlackList().contains(userToBlackList));
        doReturn(Optional.of(userToBlackList)).when(userRepository).findById(any(UUID.class));
        //then
        assertThrows(UserNotBlacklistedException.class,
                () -> blackListService.removeFromBlackList(UUID.randomUUID(), loggedUser));
    }

    @Test
    void shouldThrowExceptionWhenRemovedUserIsNotFound() {
        //given
        doReturn(Optional.empty()).when(userRepository).findById(any(UUID.class));
        //then
        assertThrows(NoSuchUserException.class,
                () -> blackListService.removeFromBlackList(UUID.randomUUID(), loggedUser));
    }
}