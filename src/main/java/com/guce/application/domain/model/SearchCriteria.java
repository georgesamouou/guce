package com.guce.application.domain.model;

import java.time.OffsetDateTime;
import java.util.UUID;

public record SearchCriteria(
        String search,
        String status,
        String treatment,
        UUID clientId,
        UUID requestTypeId,
        OffsetDateTime fromDate,
        OffsetDateTime toDate,
        int page,
        int size) {

    public SearchCriteria {
        if (page < 0)
            page = 0;
        if (size <= 0 || size > 100)
            size = 20;
    }
}