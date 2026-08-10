package com.chrisnkl.lmsauthserver.infrastructure.repository;

import com.chrisnkl.lmsauthserver.domain.model.UserIdentity;
import com.chrisnkl.lmsauthserver.domain.repository.UserIdentityRepository;
import com.chrisnkl.lmsauthserver.infrastructure.mapper.UserIdentityMapper;
import com.chrisnkl.lmsauthserver.infrastructure.security.IdentityProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserIdentityEntityRepositoryAdapter implements UserIdentityRepository {

    private final UserIdentityEntityRepository userIdentityEntityRepository;
    private final UserIdentityMapper userIdentityMapper;

    @Override
    public Optional<UserIdentity> findById(UUID id) {
        return userIdentityEntityRepository.findById(id)
                .map(userIdentityMapper::toDomain);
    }

    @Override
    public Optional<UserIdentity> findByUser(UUID userId) {
        return userIdentityEntityRepository.findByUser_Id(userId)
                .map(userIdentityMapper::toDomain);
    }

    @Override
    public Optional<UserIdentity> findBySubject(String subject) {
        return userIdentityEntityRepository.findBySubject(subject)
                .map(userIdentityMapper::toDomain);
    }

    @Override
    public Optional<UserIdentity> findByProviderAndSubject(IdentityProvider provider, String subject) {
        return userIdentityEntityRepository.findByProviderAndSubject(provider, subject)
                .map(userIdentityMapper::toDomain);
    }
}
