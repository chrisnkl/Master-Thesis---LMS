package com.chrisnkl.lmsauthserver.domain.repository;

import com.chrisnkl.lmsauthserver.domain.model.UserIdentity;
import com.chrisnkl.lmsauthserver.infrastructure.security.IdentityProvider;

import java.util.Optional;
import java.util.UUID;

public interface UserIdentityRepository {

    Optional<UserIdentity> findById(UUID id);
    Optional<UserIdentity> findByUser(UUID userId);
    Optional<UserIdentity> findBySubject(String subject);
    Optional<UserIdentity> findByProviderAndSubject(IdentityProvider provider, String subject);

}
