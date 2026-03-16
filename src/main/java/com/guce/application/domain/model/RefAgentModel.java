package com.guce.application.domain.model;

import java.util.UUID;

public record RefAgentModel(
        UUID id,
        String fullName,
        String email,
        String role,
        String title,
        boolean isActive) {
    public RefAgentModel {
        if (id == null) {
            throw new IllegalArgumentException("Invalid RefAgent id (null value)");
        }
        if (fullName == null || fullName.isBlank()) {
            throw new IllegalArgumentException("Invalid fullName (null or empty)");
        }
        if (role == null || role.isBlank()) {
            throw new IllegalArgumentException("Invalid role (null or empty)");
        }
    }
}
