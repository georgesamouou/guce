package com.guce.application.application.service;

import com.guce.application.domain.model.UploadAttachmentCommand;
import com.guce.application.domain.port.in.UploadAttachmentUseCase;
import com.guce.application.domain.service.AttachmentService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UploadAttachmentApplicationService implements UploadAttachmentUseCase {

    private final AttachmentService attachmentService;

    @Override
    public UUID execute(UUID requestId, String attachmentType, String fileName, byte[] content) {
        UploadAttachmentCommand command = new UploadAttachmentCommand(
                requestId, attachmentType, fileName, content);
        return attachmentService.upload(command);
    }
}
