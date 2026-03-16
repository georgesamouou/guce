package com.guce.application.adapter.out.persistence.mapper;

import com.guce.application.domain.model.FimexOrganizationModel;
import com.guce.application.domain.model.RefClientModel;
import com.guce.application.domain.model.RefOfficeModel;
import com.guce.application.domain.model.RefRequestTypeModel;
import com.guce.application.infrastructure.persistence.entities.FimexOrganizationEntity;
import com.guce.application.infrastructure.persistence.entities.RefClientEntity;
import com.guce.application.infrastructure.persistence.entities.RefOfficeEntity;
import com.guce.application.infrastructure.persistence.entities.RefRequestTypeEntity;
import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface RefDataPersistenceMapper {

    // ── RefRequestType ──

    RefRequestTypeEntity toEntity(RefRequestTypeModel model);

    RefRequestTypeModel toDomain(RefRequestTypeEntity entity);

    // ── RefOffice ──

    RefOfficeEntity toEntity(RefOfficeModel model);

    RefOfficeModel toDomain(RefOfficeEntity entity);

    // ── RefClient ──

    @Mapping(target = "office", source = "officeId", qualifiedByName = "uuidToOffice")
    @Mapping(target = "organization", source = "organizationId", qualifiedByName = "uuidToOrganization")
    RefClientEntity toEntity(RefClientModel model);

    @Mapping(target = "officeId", source = "office.id")
    @Mapping(target = "organizationId", source = "organization.id")
    RefClientModel toDomain(RefClientEntity entity);

    // ── FimexOrganization ──

    FimexOrganizationEntity toEntity(FimexOrganizationModel model);

    FimexOrganizationModel toDomain(FimexOrganizationEntity entity);

    // ── Helpers ──

    @Named("uuidToOffice")
    default RefOfficeEntity uuidToOffice(UUID officeId) {
        if (officeId == null)
            return null;
        RefOfficeEntity entity = new RefOfficeEntity();
        entity.setId(officeId);
        return entity;
    }

    @Named("uuidToOrganization")
    default FimexOrganizationEntity uuidToOrganization(UUID organizationId) {
        if (organizationId == null)
            return null;
        FimexOrganizationEntity entity = new FimexOrganizationEntity();
        entity.setId(organizationId);
        return entity;
    }
}
