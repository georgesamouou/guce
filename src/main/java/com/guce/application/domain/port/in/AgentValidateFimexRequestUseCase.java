package com.guce.application.domain.port.in;

import java.util.UUID;

@FunctionalInterface
public interface AgentValidateFimexRequestUseCase {
    void execute(UUID requestId, UUID agentId, String comment);
}
