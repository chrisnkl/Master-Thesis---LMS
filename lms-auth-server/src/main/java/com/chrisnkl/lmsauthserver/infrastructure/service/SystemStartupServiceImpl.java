package com.chrisnkl.lmsauthserver.infrastructure.service;

import com.chrisnkl.lmsauthserver.domain.model.SystemConfiguration;
import com.chrisnkl.lmsauthserver.domain.model.User;
import com.chrisnkl.lmsauthserver.domain.repository.SystemConfigurationRepository;
import com.chrisnkl.lmsauthserver.domain.repository.UserRepository;
import com.chrisnkl.lmsauthserver.domain.service.SystemStartupService;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SystemStartupServiceImpl implements SystemStartupService {

    private final SystemConfigurationRepository systemConfigurationRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    void startUp() {
        this.initialize();
    }

    @Override
    public void initialize() {

        SystemConfiguration defaultConfiguration = systemConfigurationRepository.findDefaultSystemConfiguration()
                .orElseThrow(() -> new IllegalStateException("Default system configuration not found"));

        log.info("Starting system initialization with default configuration: {}", defaultConfiguration);

        if (!defaultConfiguration.isSetupRequired()) {
            log.info("System Setup has already been performed since {}. Skipping..", defaultConfiguration.getUpdatedAt());
            return;
        }

        log.warn("Attention! System setup is required before proceeding!");

        User admin = userRepository.findByUsername("admin").orElseThrow(() -> new IllegalStateException("Default admin user not found"));
        log.warn("Default admin user was successfully retrieved.");

        String bootstrapToken = System.getenv("SETUP_BOOTSTRAP_TOKEN");
        if (bootstrapToken == null || bootstrapToken.isBlank()) {
            throw new IllegalStateException("Environment variable SETUP_BOOTSTRAP_TOKEN is not set or is blank. Please set it to a secure value before starting the application.");
        }

        admin.setPasswordHash(passwordEncoder.encode(bootstrapToken));
        log.warn("Registering admin user with default credentials...");
        userRepository.save(admin);

        log.warn("System Setup is required. Please login with the default admin credentials and complete the setup process.");
    }

    @Override
    public boolean isSetupRequired() {
        SystemConfiguration defaultSystemConfiguration = systemConfigurationRepository.findDefaultSystemConfiguration()
                .orElseThrow(() -> new IllegalStateException("Default system configuration not found"));

        return defaultSystemConfiguration.isSetupRequired();
    }

    @Override
    @Transactional
    public void completeSetup() {
        SystemConfiguration defaultSystemConfiguration = systemConfigurationRepository.findDefaultSystemConfiguration()
                .orElseThrow(() -> new IllegalStateException("Default system configuration not found"));

        defaultSystemConfiguration.completeSetup();

        systemConfigurationRepository.save(defaultSystemConfiguration);

    }
}
