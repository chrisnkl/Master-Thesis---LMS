package com.chrisnkl.lmsauthserver.infrastructure.mapper;

import com.chrisnkl.lmsauthserver.domain.model.Permission;
import com.chrisnkl.lmsauthserver.domain.model.Role;
import com.chrisnkl.lmsauthserver.domain.model.User;
import com.chrisnkl.lmsauthserver.infrastructure.persistence.PermissionEntity;
import com.chrisnkl.lmsauthserver.infrastructure.persistence.RoleEntity;
import com.chrisnkl.lmsauthserver.infrastructure.persistence.UserEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toDomain(UserEntity userEntity);
    UserEntity toEntity(User domain);

    Role toDomain(RoleEntity entity);
    RoleEntity toEntity(Role domain);

    Permission toDomain(PermissionEntity entity);
    PermissionEntity toEntity(Permission domain);

}
