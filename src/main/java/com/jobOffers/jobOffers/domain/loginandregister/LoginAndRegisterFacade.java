package com.jobOffers.jobOffers.domain.loginandregister;

import com.jobOffers.jobOffers.domain.loginandregister.dto.RegisterUserDto;
import com.jobOffers.jobOffers.domain.loginandregister.dto.RegistrationResultDto;
import com.jobOffers.jobOffers.domain.loginandregister.dto.UserDto;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LoginAndRegisterFacade {

    private static final String USER_NOT_FOUND = "User not found with username: ";

    private final LoginRepository loginRepository;

    public RegistrationResultDto registerUser(RegisterUserDto registerUserDto) {
        final User user = User.builder()
                .username(registerUserDto.username())
                .password(registerUserDto.password())
                .build();
        User savedUser = loginRepository.save(user);
        return new RegistrationResultDto(savedUser.id(), true, savedUser.username());
    }

    public UserDto findByUserUsername(String username) {
        return loginRepository.findByUsername(username)
                .map(user -> new UserDto(user.id(), user.username(), user.password()))
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }
}
