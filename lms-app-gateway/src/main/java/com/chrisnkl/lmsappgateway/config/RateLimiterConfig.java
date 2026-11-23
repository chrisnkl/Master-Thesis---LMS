package com.chrisnkl.lmsappgateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

import java.security.Principal;

@Configuration
public class RateLimiterConfig {

    @Bean
    public KeyResolver userKeyResolver() {
        return exchange -> exchange.getPrincipal()
                .map(Principal::getName)
                .switchIfEmpty(Mono.fromSupplier(() -> {
                    var remoteAddress = exchange.getRequest().getRemoteAddress();
                    return remoteAddress != null
                            ? remoteAddress.getAddress().getHostAddress()
                            : "anonymous";
                }));
    }
}
