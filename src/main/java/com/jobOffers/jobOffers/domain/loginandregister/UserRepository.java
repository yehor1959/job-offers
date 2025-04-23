package com.jobOffers.jobOffers.domain.loginandregister;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    User save(User user);
}
