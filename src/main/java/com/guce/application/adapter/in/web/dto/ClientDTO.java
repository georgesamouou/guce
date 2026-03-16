package com.guce.application.adapter.in.web.dto;

import java.util.UUID;

public record ClientDTO(
        UUID id,
        String taxpayerNumber,
        String companyName) {
}
