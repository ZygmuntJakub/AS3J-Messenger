package services;

import com.as3j.messenger.exceptions.NoSuchUserException;
import com.as3j.messenger.exceptions.UserWithSuchEmailExistException;
import com.as3j.messenger.model.entities.User;
import com.as3j.messenger.repositories.UserRepository;
import com.as3j.messenger.services.UserService;
import com.as3j.messenger.services.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {
    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    void shouldUpdateUser() {
        //given
        var user = new User();
        //when
        userService.update(user);
        //then
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void shouldFindAUserById() throws NoSuchUserException {
        //given
        var id = UUID.randomUUID();
        doReturn(Optional.ofNullable(new User())).when(userRepository).findById(any(UUID.class));
        //when
        userService.getById(id);
        //then
        verify(userRepository, times(1)).findById(id);
    }

    @Test
    void shouldThrowNoSuchUserException() {
        //given
        doReturn(Optional.ofNullable(null)).when(userRepository).findById(any(UUID.class));
        //then
        assertThrows(NoSuchUserException.class, () -> userService.getById(UUID.randomUUID()));
    }

    @Test
    void shouldCreateUser() throws UserWithSuchEmailExistException {
        //given
        var user = new User();
        user.setEmail("test@test.com");
        //when
        userService.create(user);
        //then
        verify(userRepository, times(1)).save(user);
    }

}
