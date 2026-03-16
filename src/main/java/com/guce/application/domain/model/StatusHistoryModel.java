package com.guce.application.domain.model;

import java.time.OffsetDateTime;
import java.util.UUID;

public record StatusHistoryModel(
        UUID id,
        UUID requestId,
        String fromStatus,
        String toStatus,
        UUID changedBy,
        String comment,
        OffsetDateTime createdAt) {
    public StatusHistoryModel {
        if (id == null) {
            throw new IllegalArgumentException("Invalid StatusHistory id (null value)");
        }
        if (requestId == null) {
            throw new IllegalArgumentException("Invalid requestId (null value)");
        }
        if (toStatus == null || toStatus.isBlank()) {
            throw new IllegalArgumentException("Invalid toStatus (null or empty)");
        }
    }
}
