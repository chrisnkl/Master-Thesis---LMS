package com.chrisnkl.lmsauthserver.domain.repository;

import com.chrisnkl.lmsauthserver.domain.model.Permission;

import java.util.Optional;

public interface PermissionRepository {

    Optional<Permission> findByName(String name);
}

