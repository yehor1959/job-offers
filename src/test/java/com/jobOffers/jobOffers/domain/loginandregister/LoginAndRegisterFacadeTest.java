package com.jobOffers.jobOffers.domain.loginandregister;

import com.jobOffers.jobOffers.domain.loginandregister.dto.RegisterUserDto;
import com.jobOffers.jobOffers.domain.loginandregister.dto.RegistrationResultDto;
import com.jobOffers.jobOffers.domain.loginandregister.dto.UserDto;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertAll;

class LoginAndRegisterFacadeTest {

    LoginAndRegisterFacade loginAndRegisterFacade = new LoginAndRegisterFacade(
            new InMemoryLoginRepository()
    );

    @Test
    public void should_register_user() {
        // given
        RegisterUserDto registerUserDto = new RegisterUserDto("username", "pass");

        // when
        RegistrationResultDto registerUser = loginAndRegisterFacade.registerUser(registerUserDto);

        // then
        assertAll(
                () -> assertThat(registerUser.created()).isTrue(),
                () -> assertThat(registerUser.username()).isEqualTo("username")
        );
    }

    @Test
    public void should_find_user_by_user_name() {
        // given
        RegisterUserDto registerUserDto = new RegisterUserDto("username", "pass");
        RegistrationResultDto register = loginAndRegisterFacade.registerUser(registerUserDto);

        // when
        UserDto userByUsername = loginAndRegisterFacade.findByUserUsername(register.username());

        // then
        assertThat(userByUsername).isEqualTo(new UserDto(register.id(), "username", "pass"));
    }

    @Test
    public void should_throw_exception_when_user_not_found() {
        // given
        String username = "someUser";

        // when
        Throwable thrown = catchThrowable(() -> loginAndRegisterFacade.findByUserUsername(username));

        // then
        AssertionsForClassTypes.assertThat(thrown)
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessage("User not found with username: someUser");
    }

}