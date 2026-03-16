package com.guce.application.domain.service;

import com.guce.application.domain.annotation.DomainService;
import com.guce.application.domain.model.CreateFimexRequestCommand;
import com.guce.application.domain.model.DesignateProductsCommand;
import com.guce.application.domain.exception.BusinessValidationException;
import com.guce.application.domain.exception.ResourceNotFoundException;
import com.guce.application.domain.model.DocumentStatus;
import com.guce.application.domain.model.FimexRequestModel;
import com.guce.application.domain.model.RefProductInfo;
import com.guce.application.domain.model.RefRequestTypeModel;
import com.guce.application.domain.port.out.FimexRequestRepositoryOutPort;
import com.guce.application.domain.port.out.FimexRequestRepositoryOutPort;
import com.guce.application.domain.port.out.FimexReferenceNumberPort;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.Year;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class FimexRequestService {

        private final FimexRequestRepositoryOutPort documentRepositoryOutPort;

        private final FimexReferenceNumberPort referenceNumberPort;

        public UUID create(CreateFimexRequestCommand command) {
                UUID id = UUID.randomUUID();
                long sequenceValue = referenceNumberPort.nextValue();
                String refFileNumber = String.format("FIM-%d-%05d", Year.now().getValue(), sequenceValue);
                LocalDate expiryDate = LocalDate.of(Year.now().getValue(), 12, 31);

                RefRequestTypeModel requestType = new RefRequestTypeModel(
                                command.requestTypeId(), "", "", true);

                List<RefProductInfo> products = command.productCodesSh() != null
                                ? command.productCodesSh().stream()
                                                .map(codeSh -> new RefProductInfo(codeSh, null))
                                                .toList()
                                : List.of();

                FimexRequestModel document = new FimexRequestModel(
                                id, null, requestType,
                                DocumentStatus.INITIATED, false, refFileNumber,
                                OffsetDateTime.now(), expiryDate,
                                products, List.of());

                var created = documentRepositoryOutPort.save(document);
                return created.id();
        }

        public void designateProducts(DesignateProductsCommand command) {
                FimexRequestModel document = documentRepositoryOutPort.findById(command.requestId())
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Request not found: " + command.requestId()));

                if (!document.client().id().equals(command.clientId())) {
                        throw new BusinessValidationException("Only the initiator can modify products.");
                }

                if (document.status() != DocumentStatus.INITIATED
                                && document.status() != DocumentStatus.COMPLEMENT_REQUESTED) {
                        throw new BusinessValidationException(
                                        "Cannot modify products in current status: " + document.status());
                }

                List<RefProductInfo> newProducts = command.productCodesSh() != null
                                ? command.productCodesSh().stream()
                                                .map(codeSh -> new RefProductInfo(codeSh, null))
                                                .toList()
                                : List.of();

                FimexRequestModel updated = new FimexRequestModel(
                                document.id(), document.client(), document.requestType(),
                                document.status(), document.isRenewal(), document.refFileNumber(),
                                document.requestedDate(), document.expiryDate(),
                                newProducts, document.attachments());

                documentRepositoryOutPort.save(updated);
        }
}
