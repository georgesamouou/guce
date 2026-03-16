package com.guce.application.adapter.in.web.dto;

import java.util.UUID;

public record RequestTypeDTO(
        UUID id,
        String code,
        String label) {
}
