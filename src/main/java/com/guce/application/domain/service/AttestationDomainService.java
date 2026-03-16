package com.guce.application.domain.service;

import com.guce.application.domain.annotation.DomainService;
import com.guce.application.domain.exception.BusinessValidationException;
import com.guce.application.domain.exception.ResourceNotFoundException;
import com.guce.application.domain.model.DocumentStatus;
import com.guce.application.domain.model.FimexRequestModel;
import com.guce.application.domain.port.out.AttestationGeneratorOutPort;
import com.guce.application.domain.port.out.FimexRequestRepositoryOutPort;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@DomainService
@RequiredArgsConstructor
public class AttestationDomainService {

    private final FimexRequestRepositoryOutPort repositoryOutPort;
    private final AttestationGeneratorOutPort attestationGeneratorOutPort;

    public byte[] generate(UUID requestId) {
        FimexRequestModel document = repositoryOutPort.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Request not found: " + requestId));

        if (DocumentStatus.SIGNED != document.status()) {
            throw new BusinessValidationException(
                    "Cannot generate attestation: document status must be SIGNED but is " + document.status());
        }

        Map<String, Object> data = new HashMap<>();
        data.put("refFileNumber", document.refFileNumber());
        data.put("requestId", document.id().toString());
        data.put("requestedDate", document.requestedDate().toString());
        data.put("expiryDate", document.expiryDate() != null
                ? document.expiryDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                : "");
        data.put("currentDate", LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        return attestationGeneratorOutPort.generate(data);
    }
}
