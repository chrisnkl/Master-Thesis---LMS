package com.chrisnkl.lmsauthserver.infrastructure.persistence;

import com.chrisnkl.lmsauthserver.infrastructure.security.IdentityProvider;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(
        name = "user_identities",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_user_identity_provider_subject",
                        columnNames = {"provider", "subject"}
                )
        }
)
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@Builder
public class UserIdentityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private IdentityProvider provider;

    @Column(nullable = false, length = 255)
    private String subject;

}
