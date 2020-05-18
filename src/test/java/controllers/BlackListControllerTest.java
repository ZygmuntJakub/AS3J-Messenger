package controllers;

import com.as3j.messenger.controllers.BlackListController;
import com.as3j.messenger.exceptions.*;
import com.as3j.messenger.model.entities.User;
import com.as3j.messenger.services.BlackListService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;

class BlackListControllerTest {

    private BlackListService blackListService;
    private BlackListController blackListController;

    @BeforeEach
    void setUp() {
        blackListService = mock(BlackListService.class);
        blackListController = new BlackListController(blackListService);
    }

    @Test
    void shouldGetBlackListTest() throws NoSuchUserException, UnauthorizedUserException {
        //given
        Set<User> blackList = Set.of(new User("test@example.com"));
        doReturn(blackList).when(blackListService).getBlackList();

        //when
        blackListController.getBlackList();
        //then

        verify(blackListService, times(1)).getBlackList();
    }

    @Test
    void shouldAddToBlackListTest() throws NoSuchUserException, UserAlreadyBlacklistedException, AttemptToBlacklistYourselfException, UnauthorizedUserException {
        //when
        UUID userId = UUID.randomUUID();
        blackListController.addToBlackList(userId);
        //then
        verify(blackListService, times(1)).addToBlackList(userId);
    }

    @Test
    void shouldThrowExceptionWhenAddOperationFails() throws UserAlreadyBlacklistedException, NoSuchUserException, AttemptToBlacklistYourselfException, UnauthorizedUserException {
        doThrow(UserAlreadyBlacklistedException.class).when(blackListService).addToBlackList(any(UUID.class));
        //then
        assertThrows(UserAlreadyBlacklistedException.class, () -> blackListController.addToBlackList(UUID.randomUUID()));
    }

    @Test
    void shouldRemoveFromBlackListTest() throws NoSuchUserException, UserNotBlacklistedException, UnauthorizedUserException {
        //when
        UUID userId = UUID.randomUUID();
        blackListController.removeFromBlackList(userId);
        //verify
        verify(blackListService, times(1)).removeFromBlackList(userId);
    }

    @Test
    void shouldThrowExceptionWhenRemoveOperationFails() throws NoSuchUserException, UserNotBlacklistedException, UnauthorizedUserException {
        //given
        doThrow(UserNotBlacklistedException.class).when(blackListService).removeFromBlackList(any(UUID.class));
        //then
        assertThrows(UserNotBlacklistedException.class, () -> blackListController.removeFromBlackList(UUID.randomUUID()));
    }

}