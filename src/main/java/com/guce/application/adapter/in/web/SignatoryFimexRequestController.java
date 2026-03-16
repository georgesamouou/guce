package com.guce.application.adapter.in.web;

import com.guce.application.adapter.in.web.dto.FimexRequestDetailDTO;
import com.guce.application.adapter.in.web.dto.FimexRequestListDTO;
import com.guce.application.adapter.in.web.mapper.FimexWebMapper;
import com.guce.application.domain.model.FimexRequestModel;
import com.guce.application.domain.model.SearchCriteria;
import com.guce.application.domain.port.in.SignatoryGetFimexRequestByIdUseCase;
import com.guce.application.domain.port.in.SignatorySearchFimexRequestsUseCase;
import com.guce.application.infrastructure.config.aop.MDCLogging;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/fimex/signatory/requests")
@RequiredArgsConstructor
@Tag(name = "FIMEX Signatory - Demandes", description = "API de traitement (Signataires)")
@PreAuthorize("hasRole('FIMEX_SIGNATORY')")
public class SignatoryFimexRequestController {

    private final SignatoryGetFimexRequestByIdUseCase signatoryGetFimexRequestByIdUseCase;
    private final SignatorySearchFimexRequestsUseCase signatorySearchFimexRequestsUseCase;
    private final FimexWebMapper webMapper;

    @GetMapping("/{id}")
    @MDCLogging
    @Operation(summary = "Consulter le détail d'une demande")
    public ResponseEntity<FimexRequestDetailDTO> getRequest(@PathVariable UUID id) {
        FimexRequestModel request = signatoryGetFimexRequestByIdUseCase.execute(id);
        return ResponseEntity.ok(webMapper.toDetailDTO(request));
    }

    @GetMapping
    @MDCLogging
    @Operation(summary = "Rechercher les demandes à traiter")
    public ResponseEntity<Page<FimexRequestListDTO>> searchRequests(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String treatment,
            @RequestParam(required = false) UUID clientId,
            @RequestParam(required = false) UUID requestTypeId,
            @RequestParam(required = false) String fromDate,
            @RequestParam(required = false) String toDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        OffsetDateTime fromDateTime = null;
        if (fromDate != null && !fromDate.isEmpty()) {
            LocalDate date = LocalDate.parse(fromDate);
            fromDateTime = date.atStartOfDay().atOffset(ZoneOffset.UTC);
        }
        OffsetDateTime toDateTime = null;
        if (toDate != null && !toDate.isEmpty()) {
            LocalDate date = LocalDate.parse(toDate);
            toDateTime = date.atTime(LocalTime.MAX).atOffset(ZoneOffset.UTC);
        }

        SearchCriteria criteria = new SearchCriteria(
                search,
                status,
                treatment,
                clientId,
                requestTypeId,
                fromDateTime,
                toDateTime,
                page,
                size);
        Page<FimexRequestListDTO> results = signatorySearchFimexRequestsUseCase.execute(criteria)
                .map(webMapper::toListDTO);
        return ResponseEntity.ok(results);
    }
}