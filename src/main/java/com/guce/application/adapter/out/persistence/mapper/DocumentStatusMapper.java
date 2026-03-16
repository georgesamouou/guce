package com.guce.application.adapter.out.persistence.mapper;

import com.guce.application.domain.model.DocumentStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface DocumentStatusMapper {

    @Named("statusToString")
    default String statusToString(DocumentStatus status) {
        return status != null ? status.name() : null;
    }

    @Named("stringToStatus")
    default DocumentStatus stringToStatus(String status) {
        return status != null ? DocumentStatus.valueOf(status) : null;
    }

    @Named("statusToTreatment")
    default String statusToTreatment(String status) {
        if (status == null)
            return null;
        return DocumentStatus.valueOf(status).getTreatmentKey();
    }
}
