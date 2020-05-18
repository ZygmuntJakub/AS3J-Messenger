package controllers;

import com.as3j.messenger.authentication.UserDetailsImpl;
import com.as3j.messenger.controllers.UserController;
import com.as3j.messenger.dto.EditUserDto;
import com.as3j.messenger.exceptions.NoSuchFileException;
import com.as3j.messenger.exceptions.NoSuchUserException;
import com.as3j.messenger.model.entities.User;
import com.as3j.messenger.services.FileService;
import com.as3j.messenger.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserControllerTest {
    private UserService userService;
    private FileService fileService;
    private UserController userController;
    private UserDetailsImpl userDetails;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        fileService = mock(FileService.class);
        userController = new UserController(userService, fileService);
        userDetails = new UserDetailsImpl("", "");
    }

    @Test
    void shouldChangeOnlyUsername() throws NoSuchUserException, NoSuchFileException {
        //given
        var requestDto = new EditUserDto("test2", null);
        var user = new User(UUID.randomUUID());
        user.setUsername("test");
        user.setAvatarPresent(false);
        doReturn(user).when(userService).getByEmail(any(String.class));
        //when
        userController.editUser(requestDto, userDetails);
        //then
        verify(userService, times(1)).update(any(User.class));
        verify(fileService, never()).updatePhoto(any(UUID.class), any(UUID.class));
        assertEquals("test2", user.getUsername());
        assertFalse(user.getAvatarPresent());
    }

    @Test
    void shouldChangeOnlyPhoto() throws NoSuchUserException, NoSuchFileException {
        //given
        var id = UUID.randomUUID();
        var requestDto = new EditUserDto(null, id);
        var user = new User(UUID.randomUUID());
        user.setUsername("test");
        user.setAvatarPresent(false);
        doReturn(user).when(userService).getByEmail(any(String.class));
        //when
        userController.editUser(requestDto, userDetails);
        //then
        verify(userService, times(1)).update(any(User.class));
        verify(fileService, times(1)).updatePhoto(any(UUID.class), any(UUID.class));
        assertEquals("test", user.getUsername());
        assertTrue(user.getAvatarPresent());
    }

    @Test
    void shouldChangeBothPhotoAndUsername() throws NoSuchUserException, NoSuchFileException {
        //given
        var id = UUID.randomUUID();
        var requestDto = new EditUserDto("test2", id);
        var user = new User(UUID.randomUUID());
        user.setUsername("test");
        user.setAvatarPresent(true);
        doReturn(user).when(userService).getByEmail(any(String.class));
        //when
        userController.editUser(requestDto, userDetails);
        //then
        verify(userService, times(1)).update(any(User.class));
        verify(fileService, times(1)).updatePhoto(any(UUID.class), any(UUID.class));
        assertEquals("test2", user.getUsername());
        assertTrue(user.getAvatarPresent());
    }
}