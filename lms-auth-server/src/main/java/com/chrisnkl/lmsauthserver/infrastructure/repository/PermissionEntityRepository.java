package com.chrisnkl.lmsauthserver.infrastructure.repository;

import com.chrisnkl.lmsauthserver.infrastructure.persistence.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PermissionEntityRepository extends JpaRepository<PermissionEntity, UUID> {
    Optional<PermissionEntity> findByName(String name);
}
