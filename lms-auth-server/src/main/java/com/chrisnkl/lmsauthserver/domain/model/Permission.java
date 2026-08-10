package com.chrisnkl.lmsauthserver.domain.model;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@Builder
@ToString
public class Permission {

    private UUID id;
    private String name;
}
