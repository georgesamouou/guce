package com.guce.application.adapter.in.web;

import com.guce.application.domain.model.AttachmentDownload;
import com.guce.application.domain.port.in.DeleteAttachmentUseCase;
import com.guce.application.domain.port.in.DownloadAttachmentUseCase;
import com.guce.application.domain.port.in.UploadAttachmentUseCase;
import com.guce.application.infrastructure.config.aop.MDCLogging;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/fimex/client/requests/{requestId}/attachments")
@RequiredArgsConstructor
@Tag(name = "FIMEX Client - Pièces Jointes", description = "Gestion des PJs pour initiateurs")
@PreAuthorize("hasAnyRole('IMPORTER', 'EXPORTER')")
public class ClientFimexAttachmentController {

    private final UploadAttachmentUseCase uploadAttachmentUseCase;
    private final DownloadAttachmentUseCase downloadAttachmentUseCase;
    private final DeleteAttachmentUseCase deleteAttachmentUseCase;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @MDCLogging
    @Operation(summary = "Ajouter une pièce jointe")
    public ResponseEntity<UUID> uploadAttachment(
            @PathVariable UUID requestId,
            @RequestParam("type") String attachmentType,
            @RequestParam("file") MultipartFile file) throws Exception {
        UUID attachmentId = uploadAttachmentUseCase.execute(requestId, attachmentType, file.getOriginalFilename(),
                file.getBytes());
        return ResponseEntity.status(HttpStatus.CREATED).body(attachmentId);
    }

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

    @DeleteMapping("/{attachmentId}")
    @MDCLogging
    @Operation(summary = "Supprimer une pièce jointe")
    public ResponseEntity<Void> deleteAttachment(
            @PathVariable UUID requestId,
            @PathVariable UUID attachmentId) {
        deleteAttachmentUseCase.execute(requestId, attachmentId);
        return ResponseEntity.noContent().build();
    }
}
