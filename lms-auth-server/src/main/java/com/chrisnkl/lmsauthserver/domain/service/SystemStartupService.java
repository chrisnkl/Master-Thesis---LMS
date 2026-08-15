package com.chrisnkl.lmsauthserver.domain.service;

public interface SystemStartupService {

    void initialize();
    boolean isSetupRequired();
    void completeSetup();

}
