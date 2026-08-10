package com.chrisnkl.lmsauthserver.infrastructure.mapper;

import com.chrisnkl.lmsauthserver.domain.model.UserIdentity;
import com.chrisnkl.lmsauthserver.infrastructure.persistence.UserIdentityEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserIdentityMapper {

    UserIdentity toDomain(UserIdentityEntity entity);
    UserIdentityEntity toEntity(UserIdentity domain);
}
