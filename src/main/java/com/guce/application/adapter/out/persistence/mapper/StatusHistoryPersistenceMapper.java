package com.guce.application.adapter.out.persistence.mapper;

import com.guce.application.domain.model.StatusHistoryModel;
import com.guce.application.infrastructure.persistence.entities.StatusHistoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StatusHistoryPersistenceMapper {

    @Mapping(target = "createdAt", ignore = true)
    StatusHistoryEntity toEntity(StatusHistoryModel model);

    StatusHistoryModel toDomain(StatusHistoryEntity entity);
}
