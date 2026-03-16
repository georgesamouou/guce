package com.guce.application.adapter.in.web;

import com.guce.application.domain.model.AttachmentDownload;
import com.guce.application.domain.port.in.DownloadAttachmentUseCase;
import com.guce.application.infrastructure.config.aop.MDCLogging;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/fimex/agent/requests/{requestId}/attachments")
@RequiredArgsConstructor
@Tag(name = "FIMEX Agent - Pièces Jointes", description = "Consultation des PJs pour agents")
@PreAuthorize("hasRole('FIMEX_AGENT')")
public class AgentFimexAttachmentController {

    private final DownloadAttachmentUseCase downloadAttachmentUseCase;

    @GetMapping("/{attachmentId}")
    @MDCLogging
    @Operation(summary = "Télécharger une pièce jointe")
    public ResponseEntity<byte[]> downloadAttachment(
            @PathVariable UUID requestId,
            @PathVariable UUID attachmentId) {
        AttachmentDownload download = downloadAttachmentUseCase.execute(requestId, attachmentId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + download.fileName() + "\"")
                .contentType(MediaType.parseMediaType(download.contentType()))
                .body(download.content());
    }
}
