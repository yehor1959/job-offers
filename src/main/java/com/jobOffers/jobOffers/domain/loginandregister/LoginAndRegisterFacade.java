package com.jobOffers.jobOffers.domain.loginandregister;

import com.jobOffers.jobOffers.domain.loginandregister.dto.RegisterUserDto;
import com.jobOffers.jobOffers.domain.loginandregister.dto.UserDto;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LoginAndRegisterFacade {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserDto registerUser(RegisterUserDto registerUserDto) {
        if (userRepository.existsByUsername(registerUserDto.getUsername())) {
            throw new IllegalArgumentException("User already exists with username: " + registerUserDto.getUsername());
        }

        User newUser = userMapper.toEntity(registerUserDto);
        User saved = userRepository.save(newUser);
        return userMapper.toDto(saved);
    }

    public UserDto findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(userMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("User not found with username: " + username));
    }
}
