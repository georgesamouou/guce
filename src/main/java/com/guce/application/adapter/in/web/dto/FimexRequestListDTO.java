package com.guce.application.adapter.in.web.dto;

import java.time.OffsetDateTime;
import java.time.LocalDate;
import java.util.UUID;

public record FimexRequestListDTO(
        UUID id,
        String refFileNumber,
        String taxpayerNumber,
        String companyName,
        String status,
        String treatment,
        OffsetDateTime requestedDate,
        LocalDate decisionDate,
        String requestTypeLabel) {
}
