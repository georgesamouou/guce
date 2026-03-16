package com.guce.application.adapter.in.web;

import com.guce.application.adapter.in.web.dto.StatusHistoryDTO;
import com.guce.application.adapter.in.web.dto.WorkflowActionRequestDTO;
import com.guce.application.domain.port.in.SignatoryRejectFimexRequestUseCase;
import com.guce.application.domain.port.in.SignatoryRequestComplementUseCase;
import com.guce.application.domain.port.in.SignatoryValidateFimexRequestUseCase;
import com.guce.application.domain.port.out.StatusHistoryOutPort;
import com.guce.application.infrastructure.config.aop.MDCLogging;
import com.guce.application.infrastructure.config.security.SecurityContextHelper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/fimex/signatory/requests/{requestId}")
@RequiredArgsConstructor
@Tag(name = "FIMEX Signatory - Workflow", description = "API de workflow pour signataires")
@PreAuthorize("hasRole('FIMEX_SIGNATORY')")
public class SignatoryFimexWorkflowController {

    private final SignatoryValidateFimexRequestUseCase signatoryValidateFimexRequestUseCase;
    private final SignatoryRejectFimexRequestUseCase signatoryRejectFimexRequestUseCase;
    private final SignatoryRequestComplementUseCase signatoryRequestComplementUseCase;
    private final StatusHistoryOutPort statusHistoryOutPort;

    @PostMapping("/validate")
    @MDCLogging
    @Operation(summary = "Valider et Signer une demande")
    public ResponseEntity<Void> validate(
            @PathVariable UUID requestId,
            @RequestBody WorkflowActionRequestDTO request) {
        UUID signatoryId = SecurityContextHelper.getCurrentUserId();
        signatoryValidateFimexRequestUseCase.execute(requestId, signatoryId, request.comment());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reject")
    @MDCLogging
    @Operation(summary = "Rejeter une demande")
    public ResponseEntity<Void> reject(
            @PathVariable UUID requestId,
            @RequestBody WorkflowActionRequestDTO request) {
        UUID signatoryId = SecurityContextHelper.getCurrentUserId();
        signatoryRejectFimexRequestUseCase.execute(requestId, signatoryId, request.comment());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/request-complement")
    @MDCLogging
    @Operation(summary = "Demander un complément")
    public ResponseEntity<Void> requestComplement(
            @PathVariable UUID requestId,
            @RequestBody WorkflowActionRequestDTO request) {
        UUID signatoryId = SecurityContextHelper.getCurrentUserId();
        signatoryRequestComplementUseCase.execute(requestId, signatoryId, request.comment());
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
}
