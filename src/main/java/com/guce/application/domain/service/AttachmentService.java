package com.guce.application.domain.service;

import com.guce.application.domain.annotation.DomainService;
import com.guce.application.domain.exception.BusinessValidationException;
import com.guce.application.domain.exception.ResourceNotFoundException;
import com.guce.application.domain.model.AttachmentDownload;
import com.guce.application.domain.model.DocumentStatus;
import com.guce.application.domain.model.FimexAttachmentModel;
import com.guce.application.domain.model.FimexRequestModel;
import com.guce.application.domain.model.UploadAttachmentCommand;
import com.guce.application.domain.port.out.AttachmentStorageOutPort;
import com.guce.application.domain.port.out.FimexRequestRepositoryOutPort;
import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class AttachmentService {

    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024; // 10 MB
    private static final Set<String> ALLOWED_MIME_TYPES = Set.of(
            "application/pdf",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
            "text/plain");
    private static final Set<DocumentStatus> MODIFIABLE_STATUSES = Set.of(
            DocumentStatus.INITIATED,
            DocumentStatus.COMPLEMENT_REQUESTED);

    private final AttachmentStorageOutPort attachmentStorageOutPort;
    private final FimexRequestRepositoryOutPort documentRepositoryOutPort;

    public UUID upload(UploadAttachmentCommand command) {
        FimexRequestModel document = findDocument(command.requestId());
        assertModifiable(document);

        if (command.content().length > MAX_FILE_SIZE) {
            throw new BusinessValidationException("File size exceeds maximum of 10 MB");
        }

        String contentType = MimeTypeDetector.detect(command.content(), command.fileName());
        validateMimeType(contentType);

        FimexAttachmentModel attachment = new FimexAttachmentModel(
                UUID.randomUUID(), command.requestId(), command.attachmentType(),
                command.fileName(), contentType, command.content(),
                "UPLOADED", OffsetDateTime.now());

        FimexAttachmentModel saved = attachmentStorageOutPort.save(attachment);
        return saved.id();
    }

    public AttachmentDownload download(UUID requestId, UUID attachmentId) {
        FimexAttachmentModel attachment = attachmentStorageOutPort.findById(attachmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Attachment not found: " + attachmentId));

        if (!attachment.requestId().equals(requestId)) {
            throw new BusinessValidationException("Attachment does not belong to this document");
        }

        return new AttachmentDownload(
                attachment.fileName(), attachment.contentType(), attachment.content());
    }

    public void delete(UUID requestId, UUID attachmentId) {
        FimexRequestModel document = findDocument(requestId);
        assertModifiable(document);

        FimexAttachmentModel attachment = attachmentStorageOutPort.findById(attachmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Attachment not found: " + attachmentId));

        if (!attachment.requestId().equals(requestId)) {
            throw new BusinessValidationException("Attachment does not belong to this document");
        }
        attachmentStorageOutPort.deleteById(attachmentId);
    }

    private FimexRequestModel findDocument(UUID requestId) {
        return documentRepositoryOutPort.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Request not found: " + requestId));
    }

    private void assertModifiable(FimexRequestModel document) {
        if (!MODIFIABLE_STATUSES.contains(document.status())) {
            throw new BusinessValidationException(
                    "Cannot modify attachments in status: " + document.status()
                            + ". Allowed statuses: " + MODIFIABLE_STATUSES);
        }
    }

    private void validateMimeType(String contentType) {
        if (!ALLOWED_MIME_TYPES.contains(contentType)) {
            throw new BusinessValidationException(
                    "File type not allowed: " + contentType + ". Allowed types: " + ALLOWED_MIME_TYPES);
        }
    }
}
