package com.guce.application.domain.port.in;

import java.util.UUID;

@FunctionalInterface
public interface UploadAttachmentUseCase {
    UUID execute(UUID requestId, String attachmentType, String fileName, byte[] content);
}
