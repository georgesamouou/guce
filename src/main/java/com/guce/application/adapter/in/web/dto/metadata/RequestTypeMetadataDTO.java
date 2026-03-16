package com.guce.application.adapter.in.web.dto.metadata;

import java.util.UUID;

public record RequestTypeMetadataDTO(
    UUID id,
    String code,
    String label
) { }