package com.chrisnkl.lmsauthserver.infrastructure.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class EntraOidcUserService extends OidcUserService {

    private final EntraUserService entraUserService;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {

        OidcUser oidcUser = super.loadUser(userRequest);
        var user = entraUserService.process(oidcUser);

        log.info("Loaded OIDC user: {}", oidcUser.getAttributes());
        log.info("Processed user: {}", user);
        return oidcUser;


//        Collection<? extends GrantedAuthority> authorities = entraUserService.getAuthorities(user);
//
//        return new DefaultOidcUser(
//                authorities,
//                oidcUser.getIdToken(),
//                oidcUser.getUserInfo()
//        );

    }
}
