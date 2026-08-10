package com.chrisnkl.lmsauthserver.infrastructure.mapper;

import com.chrisnkl.lmsauthserver.domain.model.Role;
import com.chrisnkl.lmsauthserver.infrastructure.persistence.RoleEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    Role toDomain(RoleEntity entity);
    RoleEntity toEntity(Role domain);


}
