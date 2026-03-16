package com.guce.application.domain.model;

import java.util.UUID;

public record FimexProductModel(
        UUID id,
        UUID requestId,
        String codeSh) {
    public FimexProductModel {
        if (id == null) {
            throw new IllegalArgumentException("Invalid FimexProduct id (null value)");
        }
        if (requestId == null) {
            throw new IllegalArgumentException("Invalid requestId (null value)");
        }
        if (codeSh == null || codeSh.isBlank()) {
            throw new IllegalArgumentException("Invalid codeSh (null or empty value)");
        }
    }
}
