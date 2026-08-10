package com.chrisnkl.lmsauthserver.domain.model;

import lombok.*;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@Builder
public class User {

    private UUID id;
    private String username;
    private String email;
    private String passwordHash;
    @Builder.Default
    private Set<Role> roles = new HashSet<>();

    private boolean enabled;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;

    private Instant createdAt;
    private Instant updatedAt;

}
