package com.chrisnkl.lmsauthserver.domain.service;

public interface SystemStartupService {

    boolean isSetupRequired();
    void completeSetup();

}
