package com.guce.application.adapter.in.web.dto;

import java.time.OffsetDateTime;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record FimexRequestDetailDTO(
                UUID id,
                ClientDTO client,
                RequestTypeDTO requestType,
                String status,
                boolean isRenewal,
                String refFileNumber,
                OffsetDateTime requestedDate,
                LocalDate expiryDate,
                List<ProductDTO> products,
                List<FimexAttachmentDTO> attachments) {
}
