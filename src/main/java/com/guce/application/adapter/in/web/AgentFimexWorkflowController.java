package com.guce.application.adapter.in.web;

import com.guce.application.adapter.in.web.dto.StatusHistoryDTO;
import com.guce.application.adapter.in.web.dto.WorkflowActionRequestDTO;
import com.guce.application.domain.port.in.AgentRejectFimexRequestUseCase;
import com.guce.application.domain.port.in.AgentRequestComplementUseCase;
import com.guce.application.domain.port.in.AgentValidateFimexRequestUseCase;
import com.guce.application.domain.port.in.GenerateAttestationUseCase;
import com.guce.application.domain.port.out.StatusHistoryOutPort;
import com.guce.application.infrastructure.config.aop.MDCLogging;
import com.guce.application.infrastructure.config.security.SecurityContextHelper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/fimex/agent/requests/{requestId}")
@RequiredArgsConstructor
@Tag(name = "FIMEX Agent - Workflow", description = "API de workflow pour agents")
@PreAuthorize("hasRole('FIMEX_AGENT')")
public class AgentFimexWorkflowController {

    private final AgentValidateFimexRequestUseCase agentValidateFimexRequestUseCase;
    private final AgentRejectFimexRequestUseCase agentRejectFimexRequestUseCase;
    private final AgentRequestComplementUseCase agentRequestComplementUseCase;
    private final GenerateAttestationUseCase generateAttestationUseCase;
    private final StatusHistoryOutPort statusHistoryOutPort;

    @PostMapping("/validate")
    @MDCLogging
    @Operation(summary = "Valider une demande")
    public ResponseEntity<Void> validate(
            @PathVariable UUID requestId,
            @RequestBody WorkflowActionRequestDTO request) {
        UUID agentId = SecurityContextHelper.getCurrentUserId();
        agentValidateFimexRequestUseCase.execute(requestId, agentId, request.comment());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reject")
    @MDCLogging
    @Operation(summary = "Rejeter une demande")
    public ResponseEntity<Void> reject(
            @PathVariable UUID requestId,
            @RequestBody WorkflowActionRequestDTO request) {
        UUID agentId = SecurityContextHelper.getCurrentUserId();
        agentRejectFimexRequestUseCase.execute(requestId, agentId, request.comment());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/request-complement")
    @MDCLogging
    @Operation(summary = "Demander un complément")
    public ResponseEntity<Void> requestComplement(
            @PathVariable UUID requestId,
            @RequestBody WorkflowActionRequestDTO request) {
        UUID agentId = SecurityContextHelper.getCurrentUserId();
        agentRequestComplementUseCase.execute(requestId, agentId, request.comment());
        return ResponseEntity.ok().build();
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
