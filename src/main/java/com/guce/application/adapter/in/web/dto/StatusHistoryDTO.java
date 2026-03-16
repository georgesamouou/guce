package com.guce.application.adapter.in.web.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public record StatusHistoryDTO(
        UUID id,
        String fromStatus,
        String toStatus,
        UUID changedBy,
        String comment,
        OffsetDateTime createdAt) {
}
