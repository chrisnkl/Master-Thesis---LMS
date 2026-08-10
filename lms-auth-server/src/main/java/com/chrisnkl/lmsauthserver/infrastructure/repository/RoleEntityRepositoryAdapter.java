package com.chrisnkl.lmsauthserver.infrastructure.repository;

import com.chrisnkl.lmsauthserver.domain.model.Role;
import com.chrisnkl.lmsauthserver.domain.repository.RoleRepository;
import com.chrisnkl.lmsauthserver.infrastructure.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RoleEntityRepositoryAdapter implements RoleRepository {

    private final RoleEntityRepository roleEntityRepository;
    private final UserMapper userMapper;

    @Override
    public Optional<Role> findByName(String name) {
        return roleEntityRepository.findByName(name)
                .map(userMapper::toDomain);
    }
}
