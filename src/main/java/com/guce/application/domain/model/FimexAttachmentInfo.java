package com.guce.application.domain.model;

import java.time.OffsetDateTime;
import java.util.UUID;

public record FimexAttachmentInfo(
        UUID id,
        UUID requestId,
        String attachmentType,
        String fileName,
        String contentType,
        String fileStatus,
        OffsetDateTime uploadDate) {
}
