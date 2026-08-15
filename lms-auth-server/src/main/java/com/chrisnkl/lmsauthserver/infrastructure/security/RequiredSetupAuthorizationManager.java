package com.chrisnkl.lmsauthserver.infrastructure.security;

import com.chrisnkl.lmsauthserver.domain.service.SystemStartupService;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.authorization.AuthorizationResult;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
@RequiredArgsConstructor
public class RequiredSetupAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    private final SystemStartupService systemStartupService;

    @Override
    public @Nullable AuthorizationResult authorize(Supplier<? extends @Nullable Authentication> authentication, RequestAuthorizationContext object) {

        Authentication auth = authentication.get();

        boolean setupRequired = systemStartupService.isSetupRequired();
        boolean isAuthenticated = auth != null && auth.isAuthenticated();
        boolean isAdmin = isAuthenticated && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        boolean result = setupRequired && isAuthenticated && isAdmin;

        return new AuthorizationResult() {
            @Override
            public boolean isGranted() {
                return result;
            }
        };

    }
}
