package com.guce.application.adapter.in.web.dto;

import java.util.UUID;

public record AgentDTO(
        UUID id,
        String fullName,
        String email,
        String role,
        String title) {
}
