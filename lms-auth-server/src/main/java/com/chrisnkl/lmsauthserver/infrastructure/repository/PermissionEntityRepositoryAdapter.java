package com.chrisnkl.lmsauthserver.infrastructure.repository;

import com.chrisnkl.lmsauthserver.domain.model.Permission;
import com.chrisnkl.lmsauthserver.domain.repository.PermissionRepository;
import com.chrisnkl.lmsauthserver.infrastructure.mapper.PermissionMapper;
import com.chrisnkl.lmsauthserver.infrastructure.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PermissionEntityRepositoryAdapter implements PermissionRepository {

    private final PermissionEntityRepository permissionEntityRepository;
    private final UserMapper userMapper;
    private final PermissionMapper permissionMapper;

    @Override
    public Optional<Permission> findByName(String name) {
        return permissionEntityRepository.findByName(name)
                .map(permissionMapper::toDomain);
    }
}
