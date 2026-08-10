package com.chrisnkl.lmsauthserver.infrastructure.repository;

import aj.org.objectweb.asm.commons.Remapper;
import com.chrisnkl.lmsauthserver.domain.model.UserIdentity;
import com.chrisnkl.lmsauthserver.infrastructure.persistence.UserIdentityEntity;
import com.chrisnkl.lmsauthserver.infrastructure.security.IdentityProvider;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserIdentityEntityRepository extends JpaRepository<UserIdentityEntity, UUID> {

    Optional<UserIdentityEntity> findByProviderAndSubject(
            IdentityProvider provider,
            String subject
    );
    Optional<UserIdentityEntity> findByUser_Id(UUID userId);
    Optional<UserIdentityEntity> findBySubject(String subject);
}
