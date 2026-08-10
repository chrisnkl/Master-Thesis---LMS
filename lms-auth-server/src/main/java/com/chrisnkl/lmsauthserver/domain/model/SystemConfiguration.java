package com.chrisnkl.lmsauthserver.domain.model;

import java.util.UUID;

public class SystemConfiguration {

    private UUID id;
    private boolean setupCompleted;

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
