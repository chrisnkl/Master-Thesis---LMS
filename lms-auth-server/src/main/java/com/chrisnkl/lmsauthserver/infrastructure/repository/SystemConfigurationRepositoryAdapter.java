package com.chrisnkl.lmsauthserver.infrastructure.repository;

import com.chrisnkl.lmsauthserver.domain.model.SystemConfiguration;
import com.chrisnkl.lmsauthserver.domain.repository.SystemConfigurationRepository;
import com.chrisnkl.lmsauthserver.infrastructure.mapper.SystemConfigurationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SystemConfigurationRepositoryAdapter implements SystemConfigurationRepository {

    private final SystemConfigurationEntityRepository systemConfigurationEntityRepository;
    private final SystemConfigurationMapper systemConfigurationMapper;
    private static final UUID DEFAULT_SYSTEM_CONFIGURATION_ID = UUID.fromString("00000000-0000-0000-0000-000000000001");

    @Override
    public Optional<SystemConfiguration> findSystemConfiguration(UUID id) {
        return systemConfigurationEntityRepository.findById(id)
                .map(systemConfigurationMapper::toDomain);
    }

    @Override
    public Optional<SystemConfiguration> findDefaultSystemConfiguration() {
        return findSystemConfiguration(DEFAULT_SYSTEM_CONFIGURATION_ID);
    }

    @Override
    public SystemConfiguration save(SystemConfiguration systemConfiguration) {
        return systemConfigurationMapper.toDomain(systemConfigurationEntityRepository.save(systemConfigurationMapper.toEntity(systemConfiguration)));
    }
}
