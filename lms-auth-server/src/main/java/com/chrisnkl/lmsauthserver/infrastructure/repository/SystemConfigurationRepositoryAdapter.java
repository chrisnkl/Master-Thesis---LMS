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

    @Override
    public Optional<SystemConfiguration> findSystemConfiguration(UUID id) {
        return systemConfigurationEntityRepository.findById(id)
                .map(systemConfigurationMapper::toDomain);
    }
}
