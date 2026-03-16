package com.guce.application.adapter.in.web.dto;

import java.util.List;
import java.util.UUID;

public record CreateFimexRequestDTO(
        UUID requestTypeId,
        List<String> productCodesSh) {
}
