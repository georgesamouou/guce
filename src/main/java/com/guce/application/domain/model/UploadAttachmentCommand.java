package com.guce.application.domain.model;

import java.util.UUID;

public record UploadAttachmentCommand(
        UUID requestId,
        String attachmentType,
        String fileName,
        byte[] content) {
    public UploadAttachmentCommand {
        if (requestId == null) {
            throw new IllegalArgumentException("requestId is required");
        }
        if (attachmentType == null || attachmentType.isBlank()) {
            throw new IllegalArgumentException("attachmentType is required");
        }
        if (fileName == null || fileName.isBlank()) {
            throw new IllegalArgumentException("fileName is required");
        }
        if (content == null || content.length == 0) {
            throw new IllegalArgumentException("content is required");
        }
    }
}
