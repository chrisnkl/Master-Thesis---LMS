package com.chrisnkl.lmsauthserver.domain.model;

import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@Builder
public class Role {

    private UUID id;
    private String name;
    private Boolean system;

    @Builder.Default
    private Set<Permission> permissions = new HashSet<>();
}
