package com.guce.application.domain.port.in;

import java.util.UUID;

@FunctionalInterface
public interface RenewFimexRequestUseCase {
    UUID execute(UUID requestId, UUID userId);
}
