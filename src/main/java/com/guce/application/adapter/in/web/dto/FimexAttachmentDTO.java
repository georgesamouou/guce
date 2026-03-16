package com.guce.application.adapter.in.web.dto;

import java.time.OffsetDateTime;
import java.util.UUID;

public record FimexAttachmentDTO(
                UUID id,
                String attachmentType,
                String fileName,
                String contentType,
                String fileStatus,
                OffsetDateTime uploadDate) {
}
