package com.guce.application.domain.model;

import java.util.UUID;

public record RefRequestTypeModel(
        UUID id,
        String code,
        String label,
        boolean isActive) {
    public RefRequestTypeModel {
        if (id == null) {
            throw new IllegalArgumentException("Invalid RefRequestType id (null value)");
        }
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("Invalid code (null or empty)");
        }
        if (label == null || label.isBlank()) {
            throw new IllegalArgumentException("Invalid label (null or empty)");
        }
    }
}
