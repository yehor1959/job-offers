package com.jobOffers.jobOffers.domain.loginandregister.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class RegisterUserDto {
    private final String username;
    private final String password;
}
