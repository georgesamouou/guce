package com.guce.application.domain.service;

import com.guce.application.domain.annotation.DomainService;
import com.guce.application.domain.model.DocumentStatus;
import com.guce.application.domain.model.FimexRequestModel;
import com.guce.application.domain.model.StatusHistoryModel;
import com.guce.application.domain.port.out.ClientFimexRequestQueryOutPort;
import com.guce.application.domain.port.out.FimexRequestRepositoryOutPort;
import com.guce.application.domain.port.out.FimexReferenceNumberPort;
import com.guce.application.domain.port.out.StatusHistoryOutPort;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.Year;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class RenewFimexRequestDomainService {

        private final ClientFimexRequestQueryOutPort queryOutPort;
        private final FimexRequestRepositoryOutPort repositoryOutPort;
        private final StatusHistoryOutPort statusHistoryOutPort;
        private final FimexReferenceNumberPort referenceNumberPort;

        public UUID renew(UUID requestId, UUID userId) {
                FimexRequestModel document;
        if (userId == null) {
            document = repositoryOutPort.findById(requestId)
                    .orElseThrow(() -> new IllegalArgumentException("Request not found: " + requestId));
        } else {
            document = queryOutPort.findByIdForClient(requestId, userId)
                    .orElseThrow(() -> new IllegalArgumentException("Request not found: " + requestId));
        }

        DocumentStatus current = document.status();
                current.transitionTo(DocumentStatus.INITIATED);

                UUID newrequestId = UUID.randomUUID();
                long sequenceValue = referenceNumberPort.nextValue();
                String refFileNumber = String.format("FIM-%d-%05d", Year.now().getValue(), sequenceValue);
                LocalDate expiryDate = LocalDate.of(Year.now().getValue(), 12, 31);

                FimexRequestModel renewedDocument = new FimexRequestModel(
                                newrequestId, document.client(), document.requestType(),
                                DocumentStatus.INITIATED, true, refFileNumber,
                                OffsetDateTime.now(), expiryDate,
                                document.products() != null ? document.products() : List.of(),
                                List.of());

                repositoryOutPort.save(renewedDocument);

                statusHistoryOutPort.save(new StatusHistoryModel(
                                UUID.randomUUID(), newrequestId, null, DocumentStatus.INITIATED.name(),
                                userId, "Renouvellement de la demande " + document.refFileNumber(),
                                OffsetDateTime.now()));

                return newrequestId;
        }
}
