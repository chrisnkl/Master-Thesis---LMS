package com.chrisnkl.lmsauthserver.config;

import com.chrisnkl.lmsauthserver.infrastructure.handler.LocalLoginFailureHandler;
import com.chrisnkl.lmsauthserver.infrastructure.handler.LocalLoginSuccessHandler;
import com.chrisnkl.lmsauthserver.service.EntraOidcUserService;
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
    public SecurityFilterChain securityFilterChain(HttpSecurity http, LocalLoginSuccessHandler localLoginSuccessHandler, LocalLoginFailureHandler localLoginFailureHandler, EntraOidcUserService entraOidcUserService) throws Exception {


        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorize) ->

                authorize
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/api/user/**").hasRole("USER")
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated())

                .formLogin(form -> form.loginPage("/login").permitAll()
                        .successHandler(localLoginSuccessHandler)
                        .failureHandler(localLoginFailureHandler)
                )

                .oauth2Login(oidc -> oidc
                        .loginPage("/login")
                        .userInfoEndpoint(infoEndpoint -> infoEndpoint.oidcUserService(entraOidcUserService))
                );

        return http.build();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(14);
    }

}
