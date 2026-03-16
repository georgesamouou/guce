package com.guce.application.domain.model;

import java.time.OffsetDateTime;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record FimexRequestModel(
        UUID id,
        RefClientModel client,
        RefRequestTypeModel requestType,
        DocumentStatus status,
        boolean isRenewal,
        String refFileNumber,
        OffsetDateTime requestedDate,
        LocalDate expiryDate,
        List<RefProductInfo> products,
        List<FimexAttachmentInfo> attachments) {
    public FimexRequestModel {
        if (id == null) {
            throw new IllegalArgumentException("Invalid FimexRequest id (null value)");
        }
        if (requestType == null) {
            throw new IllegalArgumentException("Invalid requestType (null value)");
        }
        if (status == null) {
            throw new IllegalArgumentException("Invalid status (null value)");
        }
        if (requestedDate == null) {
            throw new IllegalArgumentException("Invalid requestedDate (null value)");
        }
    }
}
