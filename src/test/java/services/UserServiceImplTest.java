package services;

import com.as3j.messenger.dto.ChangePasswordDto;
import com.as3j.messenger.exceptions.NoSuchUserException;
import com.as3j.messenger.exceptions.UserWithSuchEmailExistException;
import com.as3j.messenger.exceptions.WrongCurrentPasswordException;
import com.as3j.messenger.model.entities.User;
import com.as3j.messenger.repositories.UserRepository;
import com.as3j.messenger.services.UserService;
import com.as3j.messenger.services.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {
    private UserRepository userRepository;
    private UserService userService;
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        passwordEncoder = new BCryptPasswordEncoder(10);
        userService = new UserServiceImpl(userRepository, passwordEncoder);
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

    @Test
    void shouldChangePassword() throws WrongCurrentPasswordException {
        String currentPassword = "ZAQ!245sx";
        String newPassword = "Z12!2wsx";
        User user = new User(UUID.randomUUID());
        user.setPassword(passwordEncoder.encode(currentPassword));

        ChangePasswordDto changePasswordDto = new ChangePasswordDto();
        changePasswordDto.setCurrentPassword(currentPassword);
        changePasswordDto.setNewPassword(newPassword);

        //when
        userService.changePassword(user, changePasswordDto);
        //then
        verify(userRepository, times(1)).save(any(User.class));
        assertTrue(passwordEncoder.matches(newPassword, user.getPassword()));
    }

    @Test
    void shouldThrowExceptionWhenWrongCurrentPassword() {
        //given
        String currentPassword = "ZAQ!245sx";
        String newPassword = "Z12!2wsx";
        User user = new User(UUID.randomUUID());
        user.setPassword(passwordEncoder.encode(currentPassword));

        ChangePasswordDto changePasswordDto = new ChangePasswordDto();
        changePasswordDto.setCurrentPassword(currentPassword + "rt");
        changePasswordDto.setNewPassword(newPassword);

        //then
        assertThrows(WrongCurrentPasswordException.class,
                () -> userService.changePassword(user, changePasswordDto));
        verify(userRepository, times(0)).save(any(User.class));
        assertTrue(passwordEncoder.matches(currentPassword, user.getPassword()));

    }
}
