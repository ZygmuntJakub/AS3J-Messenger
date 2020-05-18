package controllers;

import com.as3j.messenger.authentication.UserDetailsImpl;
import com.as3j.messenger.controllers.BlackListController;
import com.as3j.messenger.exceptions.*;
import com.as3j.messenger.model.entities.User;
import com.as3j.messenger.services.BlackListService;
import com.as3j.messenger.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doReturn;

class BlackListControllerTest {

    private BlackListService blackListService;
    private BlackListController blackListController;
    private UserService userService;
    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        blackListService = mock(BlackListService.class);
        blackListController = new BlackListController(blackListService, userService);
        userDetails = new UserDetailsImpl("", "");
    }

    @Test
    void shouldGetBlackList() throws NoSuchUserException{
        //given
        User currentUser = new User("test@example.com");
        currentUser.setBlackList(Set.of(new User("test2@example.com"), new User("test3@example.com")));
        doReturn(currentUser).when(userService).getByEmail(anyString());
        //when
        blackListController.getBlackList(userDetails);
        //then
        verify(userService, times(1)).getByEmail(anyString());

    }

    @Test
    void shouldAddToBlackList() throws NoSuchUserException, UserAlreadyBlacklistedException,
            AttemptToBlacklistYourselfException {
        //given
        doReturn(new User("test@example.com")).when(userService).getByEmail(anyString());
        UUID userId = UUID.randomUUID();
        //when
        blackListController.addToBlackList(userId, userDetails);
        //then
        verify(blackListService, times(1)).addToBlackList(eq(userId), any(User.class));
    }

    @Test
    void shouldThrowExceptionWhenAddOperationFails() throws UserAlreadyBlacklistedException, NoSuchUserException,
            AttemptToBlacklistYourselfException {
        doReturn(new User("test@example.com")).when(userService).getByEmail(anyString());
        doThrow(UserAlreadyBlacklistedException.class).when(blackListService)
                .addToBlackList(any(UUID.class), any(User.class));
        //then
        assertThrows(UserAlreadyBlacklistedException.class,
                () -> blackListController.addToBlackList(UUID.randomUUID(), userDetails));
    }

    @Test
    void shouldRemoveFromBlackList() throws NoSuchUserException, UserNotBlacklistedException, UnauthorizedUserException {
        //given
        UUID userId = UUID.randomUUID();
        doReturn(new User("test@example.com")).when(userService).getByEmail(anyString());
        //when
        blackListController.removeFromBlackList(userId, userDetails);
        //verify
        verify(blackListService, times(1)).removeFromBlackList(eq(userId), any(User.class));
    }

    @Test
    void shouldThrowExceptionWhenRemoveOperationFails() throws NoSuchUserException, UserNotBlacklistedException {
        //given
        doReturn(new User("test@example.com")).when(userService).getByEmail(anyString());
        doThrow(UserNotBlacklistedException.class).when(blackListService)
                .removeFromBlackList(any(UUID.class), any(User.class));
        //then
        assertThrows(UserNotBlacklistedException.class,
                () -> blackListController.removeFromBlackList(UUID.randomUUID(), userDetails));
    }

}