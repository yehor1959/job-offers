package com.jobOffers.jobOffers.domain.loginandregister;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
