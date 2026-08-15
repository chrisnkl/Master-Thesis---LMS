package com.chrisnkl.lmsauthserver.domain.model;

import lombok.*;

import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@Builder
@ToString
public class Permission {

    private UUID id;
    private String name;
    private Boolean system;

    private Instant createdAt;
    private Instant updatedAt;
}
