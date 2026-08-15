package com.chrisnkl.lmsauthserver.domain.repository;

import com.chrisnkl.lmsauthserver.domain.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {

    Optional<User> findByUsername(String username);
    Optional<User> findByUserIdWithRolesAndPermissions(UUID userId);
    int updatePasswordHash(UUID id, String passwordHash);
    Optional<User> findByUsernameWithRolesAndPermissions(String username);
    Optional<User> findByEmail(String email);
    Optional<User> save(User user);
}

