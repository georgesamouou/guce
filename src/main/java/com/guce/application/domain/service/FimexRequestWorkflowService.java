package com.guce.application.domain.service;

import com.guce.application.domain.annotation.DomainService;
import com.guce.application.domain.exception.BusinessValidationException;
import com.guce.application.domain.exception.ResourceNotFoundException;
import com.guce.application.domain.model.DocumentStatus;
import com.guce.application.domain.model.FimexRequestModel;
import com.guce.application.domain.model.StatusHistoryModel;
import com.guce.application.domain.port.out.FimexRequestRepositoryOutPort;
import com.guce.application.domain.port.out.FimexRequestRepositoryOutPort;
import com.guce.application.domain.port.out.StatusHistoryOutPort;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class FimexRequestWorkflowService {

    private final FimexRequestRepositoryOutPort repositoryOutPort;
    private final StatusHistoryOutPort statusHistoryOutPort;

    public void submit(UUID requestId, UUID userId) {
        FimexRequestModel document = findDocumentForUpdate(requestId);
        if (!document.client().id().equals(userId)) {
            throw new BusinessValidationException("You are not the owner of this request.");
        }
        transition(document, DocumentStatus.SUBMITTED, userId, null);
    }

    public void validateAsAgent(UUID requestId, UUID agentId, String comment) {
        FimexRequestModel document = findDocumentForUpdate(requestId);
        if (document.status() != DocumentStatus.SUBMITTED) {
            throw new BusinessValidationException(
                    "Agent can only validate SUBMITTED requests. Current: " + document.status());
        }
        transition(document, DocumentStatus.VALIDATED_AGENT, agentId, comment);
    }

    public void validateAsSignatory(UUID requestId, UUID signatoryId, String comment) {
        FimexRequestModel document = findDocumentForUpdate(requestId);
        if (document.status() != DocumentStatus.VALIDATED_AGENT) {
            throw new BusinessValidationException(
                    "Signatory can only validate VALIDATED_AGENT requests. Current: " + document.status());
        }
        transition(document, DocumentStatus.SIGNED, signatoryId, comment);
    }

    public void rejectAsAgent(UUID requestId, UUID agentId, String reason) {
        FimexRequestModel document = findDocumentForUpdate(requestId);
        if (document.status() != DocumentStatus.SUBMITTED) {
            throw new BusinessValidationException(
                    "Agent can only reject SUBMITTED requests. Current: " + document.status());
        }
        transition(document, DocumentStatus.REJECTED, agentId, reason);
    }

    public void rejectAsSignatory(UUID requestId, UUID signatoryId, String reason) {
        FimexRequestModel document = findDocumentForUpdate(requestId);
        if (document.status() != DocumentStatus.VALIDATED_AGENT) {
            throw new BusinessValidationException(
                    "Signatory can only reject VALIDATED_AGENT requests. Current: " + document.status());
        }
        transition(document, DocumentStatus.REJECTED, signatoryId, reason);
    }

    public void requestComplementAsAgent(UUID requestId, UUID agentId, String message) {
        FimexRequestModel document = findDocumentForUpdate(requestId);
        if (document.status() != DocumentStatus.SUBMITTED) {
            throw new BusinessValidationException(
                    "Agent can only request complement for SUBMITTED requests. Current: " + document.status());
        }
        transition(document, DocumentStatus.COMPLEMENT_REQUESTED, agentId, message);
    }

    public void requestComplementAsSignatory(UUID requestId, UUID signatoryId, String message) {
        FimexRequestModel document = findDocumentForUpdate(requestId);
        if (document.status() != DocumentStatus.VALIDATED_AGENT) {
            throw new BusinessValidationException(
                    "Signatory can only request complement for VALIDATED_AGENT requests. Current: "
                            + document.status());
        }
        transition(document, DocumentStatus.COMPLEMENT_REQUESTED, signatoryId, message);
    }

    private FimexRequestModel findDocumentForUpdate(UUID requestId) {
        return repositoryOutPort.findByIdForUpdate(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Request not found: " + requestId));
    }

    private void transition(FimexRequestModel document, DocumentStatus newStatus, UUID changedBy, String comment) {
        DocumentStatus current = document.status();
        current.transitionTo(newStatus);

        statusHistoryOutPort.save(new StatusHistoryModel(
                UUID.randomUUID(), document.id(), document.status().name(), newStatus.name(),
                changedBy, comment, OffsetDateTime.now()));

        FimexRequestModel updatedDocument = new FimexRequestModel(
                document.id(), document.client(), document.requestType(),
                newStatus, document.isRenewal(), document.refFileNumber(),
                document.requestedDate(), document.expiryDate(),
                document.products(), document.attachments());

        repositoryOutPort.save(updatedDocument);
    }
}
