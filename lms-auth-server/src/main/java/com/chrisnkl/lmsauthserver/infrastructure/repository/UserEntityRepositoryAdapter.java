package com.chrisnkl.lmsauthserver.infrastructure.repository;

import com.chrisnkl.lmsauthserver.domain.model.User;
import com.chrisnkl.lmsauthserver.domain.repository.UserRepository;
import com.chrisnkl.lmsauthserver.infrastructure.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserEntityRepositoryAdapter implements UserRepository {

    private final UserEntityRepository userEntityRepository;
    private final UserMapper userMapper;

    @Override
    public Optional<User> findByUsername(String username) {
        return userEntityRepository.findByUsername(username)
                .map(userMapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userEntityRepository.findByEmail(email)
                .map(userMapper::toDomain);
    }

    @Override
    public Optional<User> save(User user) {
        return Optional.of(userMapper.toDomain(userEntityRepository.save(userMapper.toEntity(user))));
    }
}
