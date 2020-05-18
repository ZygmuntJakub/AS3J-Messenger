package controllers;

import com.as3j.messenger.controllers.UserController;
import com.as3j.messenger.dto.EditUserDTO;
import com.as3j.messenger.exceptions.NoSuchFileException;
import com.as3j.messenger.exceptions.NoSuchUserException;
import com.as3j.messenger.model.entities.User;
import com.as3j.messenger.services.FileService;
import com.as3j.messenger.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserControllerTest {
    private UserService userService;
    private FileService fileService;
    private UserController userController;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        fileService = mock(FileService.class);
        userController = new UserController(userService, fileService);
    }

    @Test
    void shouldChangeOnlyUsername() throws NoSuchUserException, NoSuchFileException {
        //given
        var requestDTO = new EditUserDTO("test2", null);
        var user = new User();
        user.setUsername("test");
        user.setAvatarPresent(false);
        doReturn(user).when(userService).getById(any(UUID.class));
        //when
        userController.editUser(UUID.randomUUID(), requestDTO);
        //then
        verify(userService, times(1)).update(any(User.class));
        verify(fileService, never()).updatePhoto(any(UUID.class), any(UUID.class));
        assertEquals("test2", user.getUsername());
        assertEquals(false, user.getAvatarPresent());
    }

    @Test
    void shouldChangeOnlyPhoto() throws NoSuchUserException, NoSuchFileException {
        //given
        var id = UUID.randomUUID();
        var requestDTO = new EditUserDTO(null, id);
        var user = new User();
        user.setUsername("test");
        user.setAvatarPresent(false);
        doReturn(user).when(userService).getById(any(UUID.class));
        //when
        userController.editUser(UUID.randomUUID(), requestDTO);
        //then
        verify(userService, times(1)).update(any(User.class));
        verify(fileService, times(1)).updatePhoto(any(UUID.class), any(UUID.class));
        assertEquals("test", user.getUsername());
        assertEquals(true, user.getAvatarPresent());
    }

    @Test
    void shouldChangeBothPhotoAndUsername() throws NoSuchUserException, NoSuchFileException {
        //given
        var id = UUID.randomUUID();
        var requestDTO = new EditUserDTO("test2", id);
        var user = new User();
        user.setUsername("test");
        user.setAvatarPresent(true);
        doReturn(user).when(userService).getById(any(UUID.class));
        //when
        userController.editUser(UUID.randomUUID(), requestDTO);
        //then
        verify(userService, times(1)).update(any(User.class));
        verify(fileService, times(1)).updatePhoto(any(UUID.class), any(UUID.class));
        assertEquals("test2", user.getUsername());
        assertEquals(true, user.getAvatarPresent());
    }
}
