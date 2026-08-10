package com.chrisnkl.lmsauthserver.infrastructure.repository;

import com.chrisnkl.lmsauthserver.infrastructure.persistence.SystemConfigurationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SystemConfigurationEntityRepository extends JpaRepository<SystemConfigurationEntity, UUID> {

    Optional<SystemConfigurationEntity> findById(UUID id);
    
}
