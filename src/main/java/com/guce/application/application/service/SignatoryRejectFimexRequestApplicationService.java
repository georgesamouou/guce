package com.guce.application.application.service;

import com.guce.application.domain.port.in.SignatoryRejectFimexRequestUseCase;
import com.guce.application.domain.service.FimexRequestWorkflowService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class SignatoryRejectFimexRequestApplicationService implements SignatoryRejectFimexRequestUseCase {

    private final FimexRequestWorkflowService workflowService;

    @Override
    public void execute(UUID requestId, UUID signatoryId, String reason) {
        workflowService.rejectAsSignatory(requestId, signatoryId, reason);
    }
}
