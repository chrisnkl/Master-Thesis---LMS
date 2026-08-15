package com.chrisnkl.lmsauthserver.service;

import com.chrisnkl.lmsauthserver.domain.model.SystemConfiguration;
import com.chrisnkl.lmsauthserver.domain.repository.SystemConfigurationRepository;
import com.chrisnkl.lmsauthserver.domain.service.SystemStartupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JpaSystemStartupService implements SystemStartupService {

    private final SystemConfigurationRepository systemConfigurationRepository;

    @Override
    public boolean isSetupRequired() {
        SystemConfiguration defaultSystemConfiguration = systemConfigurationRepository.findDefaultSystemConfiguration()
                .orElseThrow(() -> new IllegalStateException("Default system configuration not found"));

        return defaultSystemConfiguration.isSetupRequired();
    }

    @Override
    public void completeSetup() {
        SystemConfiguration defaultSystemConfiguration = systemConfigurationRepository.findDefaultSystemConfiguration()
                .orElseThrow(() -> new IllegalStateException("Default system configuration not found"));

        defaultSystemConfiguration.completeSetup();

        systemConfigurationRepository.save(defaultSystemConfiguration);

    }
}
