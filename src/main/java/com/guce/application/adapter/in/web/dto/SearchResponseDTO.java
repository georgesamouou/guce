package com.guce.application.adapter.in.web.dto;

import com.guce.application.adapter.in.web.dto.metadata.RequestTypeMetadataDTO;
import com.guce.application.adapter.in.web.dto.metadata.StatusMetadataDTO;
import com.guce.application.adapter.in.web.dto.metadata.TreatmentMetadataDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public record SearchResponseDTO(
    Page<FimexRequestListDTO> content,
    Metadata metadata
) {
    public record Metadata(
        List<RequestTypeMetadataDTO> requestTypes,
        List<StatusMetadataDTO> statuses,
        List<TreatmentMetadataDTO> treatments
    ) {}
}