package com.chrisnkl.lmsauthserver.infrastructure.mapper;

import com.chrisnkl.lmsauthserver.domain.model.Permission;
import com.chrisnkl.lmsauthserver.infrastructure.persistence.PermissionEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {

    Permission toDomain(PermissionEntity entity);
    PermissionEntity toEntity(Permission domain);


}
