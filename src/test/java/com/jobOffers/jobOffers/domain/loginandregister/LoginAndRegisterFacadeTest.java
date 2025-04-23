package com.jobOffers.jobOffers.domain.loginandregister;

import com.jobOffers.jobOffers.domain.loginandregister.dto.RegisterUserDto;
import com.jobOffers.jobOffers.domain.loginandregister.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoginAndRegisterFacadeTest {

    @Mock
    private UserRepository userRepository;

    private LoginAndRegisterFacade loginAndRegisterFacade;

    @BeforeEach
    void setUp() {
        loginAndRegisterFacade = new LoginAndRegisterFacade(userRepository, new UserMapper());
    }

    @Test
    public void should_throw_exception_when_user_not_found() {
        // given
        String nonExistingUsername = "ghostUser";
        when(userRepository.findByUsername(nonExistingUsername)).thenReturn(Optional.empty());

        // when & then
        UserNotFoundException exception = assertThrows(UserNotFoundException.class,
                () -> loginAndRegisterFacade.findUserByUsername(nonExistingUsername));

        assertEquals("User not found with username: ghostUser", exception.getMessage());
    }

    @Test
    public void should_find_user_by_user_name() {
        // given
        String username = "john_doe";
        User user = new User(username, "securePassword123");
        user.setId(1L);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        // when
        UserDto result = loginAndRegisterFacade.findUserByUsername(username);

        // then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("john_doe", result.getUsername());
    }

    @Test
    public void should_register_user_successfully_with_valid_credentials() {
        // given
        RegisterUserDto registerUserDto = new RegisterUserDto("new_user", "strongPassword!");
        User userToSave = new User("new_user", "strongPassword!");
        User savedUser = new User("new_user", "strongPassword!");
        savedUser.setId(10L);

        when(userRepository.existsByUsername("new_user")).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // when
        UserDto result = loginAndRegisterFacade.registerUser(registerUserDto);

        // then
        assertNotNull(result);
        assertEquals(10L, result.getId());
        assertEquals("new_user", result.getUsername());

        verify(userRepository).existsByUsername("new_user");
        verify(userRepository).save(any(User.class));
    }

}