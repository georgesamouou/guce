package com.guce.application.domain.model;

import java.util.UUID;

public record RefClientModel(
        UUID id,
        String taxpayerNumber,
        String companyName,
        UUID officeId,
        UUID organizationId) {
    public RefClientModel {
        if (id == null) {
            throw new IllegalArgumentException("Invalid RefClient id (null value)");
        }
        if (taxpayerNumber == null || taxpayerNumber.isBlank()) {
            throw new IllegalArgumentException("Invalid taxpayerNumber (null or empty)");
        }
    }
}
