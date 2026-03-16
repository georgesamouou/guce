package com.guce.application.domain.model;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

public record FimexRequestSummary(
        UUID id,
        String refFileNumber,
        String taxpayerNumber,
        String companyName,
        DocumentStatus status,
        String treatment,
        OffsetDateTime requestedDate,
        LocalDate decisionDate,
        String requestTypeLabel) {
}
