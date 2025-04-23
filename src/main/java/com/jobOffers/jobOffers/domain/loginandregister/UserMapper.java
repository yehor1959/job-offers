package com.jobOffers.jobOffers.domain.loginandregister;

import com.jobOffers.jobOffers.domain.loginandregister.dto.RegisterUserDto;
import com.jobOffers.jobOffers.domain.loginandregister.dto.UserDto;

public class UserMapper {
    public User toEntity(RegisterUserDto registerUserDto) {
        return new User(registerUserDto.getUsername(), registerUserDto.getPassword());
    }

    public UserDto toDto(User user) {
        return new UserDto(user.getId(), user.getUsername());
    }
}
