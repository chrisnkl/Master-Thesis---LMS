package com.chrisnkl.lmsauthserver.domain.model;

import lombok.*;

import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@Builder
public class SystemConfiguration {

    private UUID id;
    private boolean setupCompleted;

    private Instant createdAt;
    private Instant updatedAt;

    public void completeSetup() {

        if (setupCompleted) {
            throw new IllegalStateException("System setup has already been completed");
        }

        this.setupCompleted = true;
    }
    
    public boolean isSetupRequired() {
        return !setupCompleted;
    }

}
