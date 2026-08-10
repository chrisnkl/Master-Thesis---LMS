package com.chrisnkl.lmsauthserver.service;

import com.chrisnkl.lmsauthserver.infrastructure.persistence.RoleEntity;
import com.chrisnkl.lmsauthserver.infrastructure.persistence.UserEntity;
import com.chrisnkl.lmsauthserver.infrastructure.persistence.UserIdentityEntity;
import com.chrisnkl.lmsauthserver.infrastructure.repository.RoleEntityRepository;
import com.chrisnkl.lmsauthserver.infrastructure.repository.UserEntityRepository;
import com.chrisnkl.lmsauthserver.infrastructure.repository.UserIdentityEntityRepository;
import com.chrisnkl.lmsauthserver.infrastructure.security.IdentityProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class EntraUserService {

    private final UserIdentityEntityRepository userIdentityRepository;
    private final UserEntityRepository userEntityRepository;
    private final RoleEntityRepository roleEntityRepository;

    @Transactional
    public UserEntity process(OAuth2User oauthUser) {

        String subject = oauthUser.getAttribute("sub");

        if (subject == null) {
            throw new IllegalStateException(
                    "Microsoft Entra ID did not provide a subject"
            );
        }

        return userIdentityRepository
                .findByProviderAndSubject(
                        IdentityProvider.MICROSOFT,
                        subject
                )
                .map(UserIdentityEntity::getUser)
                .orElseGet(() -> createOrLinkUser(oauthUser, subject));
    }

    private UserEntity createOrLinkUser(
            OAuth2User oauthUser,
            String subject
    ) {

        String email = oauthUser.getAttribute("email");

        if (email == null) {
            email = oauthUser.getAttribute("preferred_username");
        }

        if (email == null) {
            throw new IllegalStateException(
                    "Microsoft Entra ID did not provide an email"
            );
        }

        String finalEmail = email;
        UserEntity user = userEntityRepository
                .findByEmail(email)
                .orElseGet(() -> createUser(finalEmail));

        UserIdentityEntity identity = UserIdentityEntity.builder()
                .user(user)
                .provider(IdentityProvider.MICROSOFT)
                .subject(subject)
                .build();

        userIdentityRepository.save(identity);

        return user;
    }

    private UserEntity createUser(String email) {

        RoleEntity userRole = roleEntityRepository
                .findByName("USER")
                .orElseThrow(() ->
                        new IllegalStateException(
                                "Default USER role does not exist"
                        )
                );

        UserEntity user = UserEntity.builder()
                .username(email)
                .email(email)
                .passwordHash(null)
                .enabled(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .roles(Set.of(userRole))
                .build();

        return userEntityRepository.save(user);
    }

    @Transactional
    public Collection<? extends GrantedAuthority> getAuthorities(UserEntity user) {

        List<GrantedAuthority> authorities = new ArrayList<>();

        user.getRoles().forEach(role -> {

            authorities.add(
                    new SimpleGrantedAuthority(
                            "ROLE_" + role.getName()
                    )
            );

            role.getPermissions().forEach(permission ->
                    authorities.add(
                            new SimpleGrantedAuthority(permission.getName())
                    )
            );
        });

        return authorities;

    }
}
