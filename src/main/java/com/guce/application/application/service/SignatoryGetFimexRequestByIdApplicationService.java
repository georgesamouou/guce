package com.guce.application.application.service;

import com.guce.application.domain.model.FimexRequestModel;
import com.guce.application.domain.port.in.SignatoryGetFimexRequestByIdUseCase;
import com.guce.application.domain.service.FimexRequestQueryService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SignatoryGetFimexRequestByIdApplicationService implements SignatoryGetFimexRequestByIdUseCase {

    private final FimexRequestQueryService queryService;

    @Override
    public FimexRequestModel execute(UUID requestId) {
        return queryService.findByIdForSignatory(requestId);
    }
}
