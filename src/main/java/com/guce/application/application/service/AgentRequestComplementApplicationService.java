package com.guce.application.application.service;

import com.guce.application.domain.port.in.AgentRequestComplementUseCase;
import com.guce.application.domain.service.FimexRequestWorkflowService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AgentRequestComplementApplicationService implements AgentRequestComplementUseCase {

    private final FimexRequestWorkflowService workflowService;

    @Override
    public void execute(UUID requestId, UUID agentId, String message) {
        workflowService.requestComplementAsAgent(requestId, agentId, message);
    }
}
