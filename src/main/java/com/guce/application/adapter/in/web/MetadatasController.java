package com.guce.application.adapter.in.web;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

import com.guce.application.adapter.in.web.dto.FimexRequestListDTO;
import com.guce.application.adapter.in.web.dto.SearchResponseDTO;
import com.guce.application.adapter.in.web.dto.metadata.RequestTypeMetadataDTO;
import com.guce.application.adapter.in.web.dto.metadata.StatusMetadataDTO;
import com.guce.application.adapter.in.web.dto.metadata.TreatmentMetadataDTO;
import com.guce.application.domain.service.MetadataService;
import com.guce.application.infrastructure.config.aop.MDCLogging;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/fimex/metadatas")
@RequiredArgsConstructor
@Tag(name = "FIMEX - Lister les metadonnées", description = "Controleur de gestion des métadonnées")
@PreAuthorize("hasAnyRole('IMPORTER', 'EXPORTER', 'FIMEX_AGENT', 'FIMEX_SIGNATORY')")
public class MetadatasController {

    private final MetadataService metadataService;

    @GetMapping
    @MDCLogging
    @Operation(summary = "Cet endpoint permet de retourner toutes les métadonnées (request types, statuses, treatments")
    public ResponseEntity<SearchResponseDTO.Metadata> giveMetadatas() {
        List<RequestTypeMetadataDTO> requestTypes = metadataService.getAllRequestTypes();
        List<StatusMetadataDTO> statuses = metadataService.getAllStatuses();
        List<TreatmentMetadataDTO> treatments = metadataService.getAllTreatments();

        return ResponseEntity.ok(
            new SearchResponseDTO.Metadata(requestTypes, statuses, treatments)
        );
    }
}
