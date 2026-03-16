package com.guce.application.adapter.out.persistence.mapper;

import com.guce.application.domain.model.FimexAttachmentModel;
import com.guce.application.infrastructure.persistence.entities.FimexAttachmentEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FimexAttachmentPersistenceMapper {

    FimexAttachmentEntity toEntity(FimexAttachmentModel model);

    FimexAttachmentModel toDomain(FimexAttachmentEntity entity);
}
