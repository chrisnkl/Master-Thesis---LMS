package com.chrisnkl.lmsauthserver.domain.repository;

import com.chrisnkl.lmsauthserver.domain.model.User;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> save(User user);
}

