package com.jobOffers.jobOffers.domain.loginandregister.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class UserDto {
    private final Long id;
    private final String username;
}
