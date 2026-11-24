package com.chrisnkl.lmsappgateway.config;

import io.netty.handler.codec.http.cookie.CookieHeaderNames;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcReactiveOAuth2UserService;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.web.server.logout.OidcClientInitiatedServerLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.ReactiveOAuth2UserService;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestCustomizers;
import org.springframework.security.oauth2.client.web.server.DefaultServerOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.HttpStatusServerEntryPoint;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.csrf.CookieServerCsrfTokenRepository;
import org.springframework.security.web.server.csrf.CsrfToken;
import org.springframework.security.web.server.csrf.ServerCsrfTokenRequestAttributeHandler;
import org.springframework.security.web.server.header.ReferrerPolicyServerHttpHeadersWriter;
import org.springframework.security.web.server.header.XFrameOptionsServerHttpHeadersWriter;
import org.springframework.web.server.WebFilter;
import reactor.core.publisher.Mono;

import java.util.*;

@EnableWebFluxSecurity
@Configuration
public class SecurityConfig {

    @Value("${app.frontend-url:}")
    private String frontendUrl;

    @Value("${server.reactive.session.cookie.secure:true}")
    private boolean cookieSecure;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http, ReactiveClientRegistrationRepository clientRegistrationRepository) {


        // Enable PKCE
        var authorizationRequestResolver = new DefaultServerOAuth2AuthorizationRequestResolver(clientRegistrationRepository);
        authorizationRequestResolver.setAuthorizationRequestCustomizer(OAuth2AuthorizationRequestCustomizers.withPkce());

        // OIDC RP-Initiated Logout
        var logoutSuccessHandler = new OidcClientInitiatedServerLogoutSuccessHandler(clientRegistrationRepository);
        logoutSuccessHandler.setPostLogoutRedirectUri(frontendUrl);

        CookieServerCsrfTokenRepository csrfTokenRepository = CookieServerCsrfTokenRepository.withHttpOnlyFalse();
        csrfTokenRepository.setCookieCustomizer(cookie ->
                cookie.path("/").sameSite(CookieHeaderNames.SameSite.Lax.toString()).secure(cookieSecure));

        return http

                .headers(headers ->
                        headers.frameOptions(frame ->
                                frame.mode(XFrameOptionsServerHttpHeadersWriter.Mode.DENY))
                                .contentTypeOptions(Customizer.withDefaults())
                                .referrerPolicy(referrer ->
                                        referrer.policy(ReferrerPolicyServerHttpHeadersWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN)))

                .csrf(csrf ->
                        csrf.csrfTokenRepository(csrfTokenRepository)
                                .csrfTokenRequestHandler(new ServerCsrfTokenRequestAttributeHandler()))

                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(new HttpStatusServerEntryPoint(HttpStatus.UNAUTHORIZED)))

                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers(
                                "/actuator/health/liveness",
                                "/actuator/health/readiness",   
                                "/oauth2/**",
                                "/login/**",
                                "/bff/me",
                                "/api/lang/**",
                                "/fallback/**"
                        ).permitAll()
                        .pathMatchers("/actuator/**").hasRole("ADMIN")
                        .pathMatchers("/api/v1/admin/**").hasRole("ADMIN")
                        .pathMatchers("/api/**").authenticated()
                        .anyExchange().denyAll())

                // OAuth2 BFF Login with PKCE -> redirects back to React frontend
                .oauth2Login(oauth2 ->
                        oauth2.authorizationRequestResolver(authorizationRequestResolver)
                                .authenticationSuccessHandler(new RedirectServerAuthenticationSuccessHandler(frontendUrl)))

                // Full OIDC Logout
                .logout(logout ->
                        logout.logoutSuccessHandler(logoutSuccessHandler))

                .build();
    }

    /**
     * Subscribes to the deferred CsrfToken Mono on every request so the
     * XSRF-TOKEN cookie is eagerly written on initial GET requests (e.g. GET /bff/me).
     */
    @Bean
    public WebFilter csrfCookieWebFilter() {
        return (exchange, chain) -> {
            Mono<CsrfToken> csrfToken = exchange.getAttributeOrDefault(CsrfToken.class.getName(), Mono.empty());
            return csrfToken.doOnSuccess(token -> {}).then(chain.filter(exchange));
        };
    }
    /**
     * Maps Keycloak's realm_access.roles from the ID token into Spring Security ROLE_* authorities
     * so the Gateway can enforce coarse-grained role rules (e.g. hasRole("ADMIN")).
     */
    @Bean
    public ReactiveOAuth2UserService<OidcUserRequest, OidcUser> keycloakOidcUserService() {
        OidcReactiveOAuth2UserService delegate = new OidcReactiveOAuth2UserService();
        return userRequest -> delegate.loadUser(userRequest).map(oidcUser -> {
            Set<GrantedAuthority> mappedAuthorities = new HashSet<>(oidcUser.getAuthorities());
            Map<String, Object> realmAccess = oidcUser.getClaimAsMap("realm_access");
            if (realmAccess != null && realmAccess.get("roles") instanceof Collection<?> roles) {
                for (Object roleObj : roles) {
                    if (roleObj instanceof String role && !role.isBlank()) {
                        String normalized = role.toUpperCase(Locale.ROOT);
                        String authority = normalized.startsWith("ROLE_") ? normalized : "ROLE_" + normalized;
                        mappedAuthorities.add(new SimpleGrantedAuthority(authority));
                    }
                }
            }
            return new DefaultOidcUser(
                    mappedAuthorities,
                    oidcUser.getIdToken(),
                    oidcUser.getUserInfo(),
                    "preferred_username"
            );
        });
    }
}
