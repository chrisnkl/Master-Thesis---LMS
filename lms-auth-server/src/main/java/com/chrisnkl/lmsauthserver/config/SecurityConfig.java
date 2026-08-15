package com.chrisnkl.lmsauthserver.config;

import com.chrisnkl.lmsauthserver.infrastructure.handler.LocalLoginFailureHandler;
import com.chrisnkl.lmsauthserver.infrastructure.handler.LocalLoginSuccessHandler;
import com.chrisnkl.lmsauthserver.infrastructure.security.RequiredSetupAuthorizationManager;
import com.chrisnkl.lmsauthserver.infrastructure.service.EntraOidcUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    @Order(2)
    public SecurityFilterChain securityFilterChain(HttpSecurity http, LocalLoginSuccessHandler localLoginSuccessHandler, LocalLoginFailureHandler localLoginFailureHandler, EntraOidcUserService entraOidcUserService, RequiredSetupAuthorizationManager requiredSetupAuthorizationManager) throws Exception {


        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorize) ->

                authorize
                        .requestMatchers("/auth/login").permitAll()
                        .requestMatchers("/setup").access(requiredSetupAuthorizationManager)
                        .requestMatchers("/api/v1/user/**").hasRole("USER")
                        .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated())

                .formLogin(form -> form
                        .loginPage("/auth/login")
                        .loginProcessingUrl("/login")
                        .permitAll()
                        .successHandler(localLoginSuccessHandler)
                        .failureHandler(localLoginFailureHandler)
                )

                .oauth2Login(oidc -> oidc
                        .loginPage("/auth/login")
                        .userInfoEndpoint(infoEndpoint -> infoEndpoint.oidcUserService(entraOidcUserService))
                );

        return http.build();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(14);
    }

}
