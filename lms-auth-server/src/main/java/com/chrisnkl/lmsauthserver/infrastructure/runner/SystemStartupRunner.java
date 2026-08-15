package com.chrisnkl.lmsauthserver.infrastructure.runner;

import com.chrisnkl.lmsauthserver.domain.service.SystemStartupService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SystemStartupRunner {

    private final SystemStartupService systemStartupService;

    @EventListener(ApplicationReadyEvent.class)
    public void startUp() {
        systemStartupService.initialize();
    }
}
