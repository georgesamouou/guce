package com.guce.application.application.service;

import com.guce.application.domain.port.in.DeleteAttachmentUseCase;
import com.guce.application.domain.service.AttachmentService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DeleteAttachmentApplicationService implements DeleteAttachmentUseCase {

    private final AttachmentService attachmentService;

    @Override
    public void execute(UUID requestId, UUID attachmentId) {
        attachmentService.delete(requestId, attachmentId);
    }
}
