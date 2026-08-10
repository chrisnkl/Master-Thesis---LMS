package com.chrisnkl.lmsauthserver.infrastructure.mapper;

import com.chrisnkl.lmsauthserver.domain.model.SystemConfiguration;
import com.chrisnkl.lmsauthserver.infrastructure.persistence.SystemConfigurationEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SystemConfigurationMapper {

    SystemConfiguration toDomain(SystemConfigurationEntity entity);
    SystemConfigurationEntity toEntity(SystemConfiguration domain);

}
