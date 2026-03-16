package com.guce.application.domain.model;

public record RefProductInfo(
        String codeSh,
        String description) {
    public RefProductInfo {
        if (codeSh == null || codeSh.isBlank()) {
            throw new IllegalArgumentException("Invalid codeSh (null or empty value)");
        }
    }
}
