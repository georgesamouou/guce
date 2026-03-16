package com.guce.application.domain.port.in;

import java.util.UUID;

@FunctionalInterface
public interface SignatoryRejectFimexRequestUseCase {
    void execute(UUID requestId, UUID signatoryId, String reason);
}
