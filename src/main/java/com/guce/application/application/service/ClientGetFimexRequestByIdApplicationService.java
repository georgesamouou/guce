package com.guce.application.application.service;

import com.guce.application.domain.model.FimexRequestModel;
import com.guce.application.domain.port.in.ClientGetFimexRequestByIdUseCase;
import com.guce.application.domain.service.FimexRequestQueryService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClientGetFimexRequestByIdApplicationService implements ClientGetFimexRequestByIdUseCase {

    private final FimexRequestQueryService queryService;

    @Override
    public FimexRequestModel execute(UUID requestId, UUID clientId) {
        return queryService.findByIdForClient(requestId, clientId);
    }
}
