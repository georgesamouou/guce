package com.guce.application.domain.port.in;

import java.util.UUID;

@FunctionalInterface
public interface AgentRequestComplementUseCase {
    void execute(UUID requestId, UUID agentId, String message);
}
