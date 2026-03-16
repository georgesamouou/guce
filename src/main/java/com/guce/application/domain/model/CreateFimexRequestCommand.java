package com.guce.application.domain.model;

import java.util.List;
import java.util.UUID;

public record CreateFimexRequestCommand(
        UUID clientId,
        UUID requestTypeId,
        List<String> productCodesSh) {
    public CreateFimexRequestCommand {
        if (requestTypeId == null) {
            throw new IllegalArgumentException("requestTypeId is required");
        }
    }
}
