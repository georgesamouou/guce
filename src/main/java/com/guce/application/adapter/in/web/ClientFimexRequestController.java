package com.guce.application.adapter.in.web;

import com.guce.application.adapter.in.web.dto.CreateFimexRequestDTO;
import com.guce.application.adapter.in.web.dto.DesignateProductsDTO;
import com.guce.application.adapter.in.web.dto.FimexRequestDetailDTO;
import com.guce.application.adapter.in.web.dto.FimexRequestListDTO;
import com.guce.application.adapter.in.web.mapper.FimexWebMapper;
import com.guce.application.domain.model.CreateFimexRequestCommand;
import com.guce.application.domain.model.DesignateProductsCommand;
import com.guce.application.domain.model.FimexRequestModel;
import com.guce.application.domain.model.FimexRequestSummary;
import com.guce.application.domain.model.SearchCriteria;
import com.guce.application.domain.port.in.CreateFimexRequestUseCase;
import com.guce.application.domain.port.in.DesignateProductsUseCase;
import com.guce.application.domain.port.in.ClientGetFimexRequestByIdUseCase;
import com.guce.application.domain.port.in.ClientSearchFimexRequestsUseCase;
import com.guce.application.domain.port.in.RenewFimexRequestUseCase;
import com.guce.application.infrastructure.config.aop.MDCLogging;
import com.guce.application.infrastructure.config.security.SecurityContextHelper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/fimex/client/requests")
@RequiredArgsConstructor
@Tag(name = "FIMEX Client - Demandes", description = "API initiateurs (Importateurs/Exportateurs)")
@PreAuthorize("hasAnyRole('IMPORTER', 'EXPORTER')")
public class ClientFimexRequestController {

    private final CreateFimexRequestUseCase createFimexRequestUseCase;
    private final ClientGetFimexRequestByIdUseCase clientGetFimexRequestByIdUseCase;
    private final ClientSearchFimexRequestsUseCase clientSearchFimexRequestsUseCase;
    private final RenewFimexRequestUseCase renewFimexRequestUseCase;
    private final DesignateProductsUseCase designateProductsUseCase;
    private final FimexWebMapper webMapper;

    @PostMapping
    @MDCLogging
    @Operation(summary = "Créer une demande FIMEX")
    @ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Demande créée") })
    public ResponseEntity<UUID> createRequest(@RequestBody CreateFimexRequestDTO dto) {
        UUID clientId = SecurityContextHelper.getCurrentUserId();
        CreateFimexRequestCommand command = new CreateFimexRequestCommand(
                clientId,
                dto.requestTypeId(),
                dto.productCodesSh());
        UUID id = createFimexRequestUseCase.execute(command);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @PostMapping("/{taxpayerNumber}/renew")
    @MDCLogging
    @Operation(summary = "Renouveler la dernière demande du contribuable par taxpayerNumber")
    @ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Nouvelle demande créée") })
    public ResponseEntity<UUID> renewByTaxpayerPath(@PathVariable String taxpayerNumber) {
        UUID clientId = SecurityContextHelper.getCurrentUserId();

        SearchCriteria criteria = new SearchCriteria(
                taxpayerNumber,
                null,
                null,
                clientId,
                null,
                null,
                null,
                0,
                1);

        FimexRequestSummary existing = clientSearchFimexRequestsUseCase.execute(criteria)
                .stream()
                .filter(s -> s.taxpayerNumber().equalsIgnoreCase(taxpayerNumber))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Demande existante introuvable pour le taxpayerNumber : " + taxpayerNumber));

        UUID renewedRequestId = renewFimexRequestUseCase.execute(existing.id(), clientId);
        return ResponseEntity.status(HttpStatus.CREATED).body(renewedRequestId);
    }

    @GetMapping("/{id}")
    @MDCLogging
    @Operation(summary = "Consulter sa demande")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Détail de la demande") })
    public ResponseEntity<FimexRequestDetailDTO> getRequest(@PathVariable UUID id) {
        UUID clientId = SecurityContextHelper.getCurrentUserId();
        FimexRequestModel request = clientGetFimexRequestByIdUseCase.execute(id, clientId);
        return ResponseEntity.ok(webMapper.toDetailDTO(request));
    }

    @GetMapping
    @MDCLogging
    @Operation(summary = "Lister ses demandes")
    public ResponseEntity<Page<FimexRequestListDTO>> searchRequests(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String treatment,
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

        UUID clientId = SecurityContextHelper.getCurrentUserId();
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
        Page<FimexRequestListDTO> results = clientSearchFimexRequestsUseCase.execute(criteria)
                .map(webMapper::toListDTO);
        return ResponseEntity.ok(results);
    }

    @PutMapping("/{id}/products")
    @MDCLogging
    @Operation(summary = "Désigner les produits liés")
    public ResponseEntity<Void> designateProducts(
            @PathVariable UUID id,
            @RequestBody DesignateProductsDTO dto) {
        UUID clientId = SecurityContextHelper.getCurrentUserId();
        designateProductsUseCase.execute(new DesignateProductsCommand(id, clientId, dto.productCodesSh()));
        return ResponseEntity.ok().build();
    }
}