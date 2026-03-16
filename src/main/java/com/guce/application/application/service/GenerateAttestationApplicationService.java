package com.guce.application.application.service;

import com.guce.application.domain.port.in.GenerateAttestationUseCase;
import com.guce.application.domain.service.AttestationDomainService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GenerateAttestationApplicationService implements GenerateAttestationUseCase {

    private final AttestationDomainService attestationDomainService;

    @Override
    public byte[] execute(UUID requestId) {
        return attestationDomainService.generate(requestId);
    }
}
