package com.chrisnkl.lmsauthserver.domain.repository;

import com.chrisnkl.lmsauthserver.domain.model.SystemConfiguration;

import java.util.Optional;
import java.util.UUID;

public interface SystemConfigurationRepository {

    Optional<SystemConfiguration> findSystemConfiguration(UUID id);

}
