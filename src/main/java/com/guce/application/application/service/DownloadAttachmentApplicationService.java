package com.guce.application.application.service;

import com.guce.application.domain.model.AttachmentDownload;
import com.guce.application.domain.port.in.DownloadAttachmentUseCase;
import com.guce.application.domain.service.AttachmentService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DownloadAttachmentApplicationService implements DownloadAttachmentUseCase {

    private final AttachmentService attachmentService;

    @Override
    public AttachmentDownload execute(UUID requestId, UUID attachmentId) {
        return attachmentService.download(requestId, attachmentId);
    }
}
