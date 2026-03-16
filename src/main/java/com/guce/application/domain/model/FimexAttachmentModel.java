package com.guce.application.domain.model;

import java.time.OffsetDateTime;
import java.util.UUID;

public record FimexAttachmentModel(
        UUID id,
        UUID requestId,
        String attachmentType,
        String fileName,
        String contentType,
        byte[] content,
        String fileStatus,
        OffsetDateTime uploadDate) {
    public FimexAttachmentModel {
        if (id == null) {
            throw new IllegalArgumentException("Invalid FimexAttachment id (null value)");
        }
        if (requestId == null) {
            throw new IllegalArgumentException("Invalid requestId (null value)");
        }
        if (attachmentType == null || attachmentType.isBlank()) {
            throw new IllegalArgumentException("Invalid attachmentType (null or empty)");
        }
        if (fileName == null || fileName.isBlank()) {
            throw new IllegalArgumentException("Invalid fileName (null or empty)");
        }
    }
}
