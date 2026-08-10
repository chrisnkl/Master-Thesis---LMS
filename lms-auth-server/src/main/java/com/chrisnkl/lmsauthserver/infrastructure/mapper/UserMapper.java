package com.chrisnkl.lmsauthserver.infrastructure.mapper;

import com.chrisnkl.lmsauthserver.domain.model.User;
import com.chrisnkl.lmsauthserver.infrastructure.persistence.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toDomain(UserEntity userEntity);
    UserEntity toEntity(User domain);


}
