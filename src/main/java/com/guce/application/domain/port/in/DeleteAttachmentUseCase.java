package com.guce.application.domain.port.in;

import java.util.UUID;

@FunctionalInterface
public interface DeleteAttachmentUseCase {
    void execute(UUID requestId, UUID attachmentId);
}
