package com.guce.application.domain.port.in;

import java.util.UUID;

@FunctionalInterface
public interface SubmitFimexRequestUseCase {
    void execute(UUID requestId, UUID userId);
}
