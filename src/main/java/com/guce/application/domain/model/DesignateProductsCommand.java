package com.guce.application.domain.model;

import java.util.List;
import java.util.UUID;

public record DesignateProductsCommand(
        UUID requestId,
        UUID clientId,
        List<String> productCodesSh) {
}
