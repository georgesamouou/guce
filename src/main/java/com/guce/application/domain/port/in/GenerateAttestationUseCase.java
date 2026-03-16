package com.guce.application.domain.port.in;

import java.util.UUID;

@FunctionalInterface
public interface GenerateAttestationUseCase {
    byte[] execute(UUID requestId);
}
