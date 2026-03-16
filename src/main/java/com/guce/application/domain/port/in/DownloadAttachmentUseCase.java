package com.guce.application.domain.port.in;

import com.guce.application.domain.model.AttachmentDownload;
import java.util.UUID;

@FunctionalInterface
public interface DownloadAttachmentUseCase {
    AttachmentDownload execute(UUID requestId, UUID attachmentId);
}
