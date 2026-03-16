package com.guce.application.adapter.in.web;

import com.guce.application.adapter.in.web.dto.StatusHistoryDTO;
import com.guce.application.domain.port.in.GenerateAttestationUseCase;
import com.guce.application.domain.port.in.RenewFimexRequestUseCase;
import com.guce.application.domain.port.in.SubmitFimexRequestUseCase;
import com.guce.application.domain.port.out.StatusHistoryOutPort;
import com.guce.application.infrastructure.config.aop.MDCLogging;
import com.guce.application.infrastructure.config.security.SecurityContextHelper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/fimex/client/requests/{requestId}")
@RequiredArgsConstructor
@Tag(name = "FIMEX Client - Workflow", description = "API de workflow pour initiateurs")
@PreAuthorize("hasAnyRole('IMPORTER', 'EXPORTER')")
public class ClientFimexWorkflowController {

    private final SubmitFimexRequestUseCase submitFimexRequestUseCase;
    private final RenewFimexRequestUseCase renewFimexRequestUseCase;
    private final GenerateAttestationUseCase generateAttestationUseCase;
    private final StatusHistoryOutPort statusHistoryOutPort;

    @PostMapping("/submit")
    @MDCLogging
    @Operation(summary = "Soumettre une demande")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Demande soumise") })
    public ResponseEntity<Void> submit(@PathVariable UUID requestId) {
        UUID userId = SecurityContextHelper.getCurrentUserId();
        submitFimexRequestUseCase.execute(requestId, userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/renew")
    @MDCLogging
    @Operation(summary = "Renouveler une demande")
    @ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Nouvelle demande créée") })
    public ResponseEntity<UUID> renew(@PathVariable UUID requestId) {
        UUID clientId = SecurityContextHelper.getCurrentUserId();
        UUID newrequestId = renewFimexRequestUseCase.execute(requestId, clientId);
        return ResponseEntity.status(HttpStatus.CREATED).body(newrequestId);
    }

    @GetMapping("/history")
    @MDCLogging
    @Operation(summary = "Consulter l'historique")
    public ResponseEntity<List<StatusHistoryDTO>> getHistory(@PathVariable UUID requestId) {
        List<StatusHistoryDTO> history = statusHistoryOutPort.findByRequestId(requestId)
                .stream()
                .map(h -> new StatusHistoryDTO(h.id(), h.fromStatus(), h.toStatus(), h.changedBy(), h.comment(),
                        h.createdAt()))
                .toList();
        return ResponseEntity.ok(history);
    }

    @GetMapping("/attestation")
    @MDCLogging
    @Operation(summary = "Télécharger l'attestation FIMEX")
    public ResponseEntity<byte[]> downloadAttestation(@PathVariable UUID requestId) {
        byte[] pdf = generateAttestationUseCase.execute(requestId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=attestation_fimex.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}
