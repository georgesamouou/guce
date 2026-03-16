package com.guce.application.application.service;

import com.guce.application.domain.port.in.RenewFimexRequestUseCase;
import com.guce.application.domain.service.RenewFimexRequestDomainService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RenewFimexRequestApplicationService implements RenewFimexRequestUseCase {

    private final RenewFimexRequestDomainService renewDomainService;

    @Override
    public UUID execute(UUID requestId, UUID userId) {
        return renewDomainService.renew(requestId, userId);
    }
}
