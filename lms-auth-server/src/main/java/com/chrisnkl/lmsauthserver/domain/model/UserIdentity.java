package com.chrisnkl.lmsauthserver.domain.model;

import com.chrisnkl.lmsauthserver.infrastructure.security.IdentityProvider;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@Builder
public class UserIdentity {

    private UUID id;
    private User user;
    private IdentityProvider provider;
    private String subject;

    private Instant createdAt;
    private Instant updatedAt;
}
