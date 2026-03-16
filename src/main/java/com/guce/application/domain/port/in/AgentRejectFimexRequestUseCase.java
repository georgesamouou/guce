package com.guce.application.domain.port.in;

import java.util.UUID;

@FunctionalInterface
public interface AgentRejectFimexRequestUseCase {
    void execute(UUID requestId, UUID agentId, String reason);
}
