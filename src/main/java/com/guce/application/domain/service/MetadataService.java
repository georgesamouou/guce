package com.guce.application.domain.service;

import com.guce.application.adapter.in.web.dto.metadata.RequestTypeMetadataDTO;
import com.guce.application.adapter.in.web.dto.metadata.StatusMetadataDTO;
import com.guce.application.adapter.in.web.dto.metadata.TreatmentMetadataDTO;
import com.guce.application.domain.annotation.DomainService;
import com.guce.application.domain.model.DocumentStatus;
import com.guce.application.infrastructure.persistence.repository.RefRequestTypeJpaRepository;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@DomainService
@RequiredArgsConstructor
public class MetadataService {

    private final RefRequestTypeJpaRepository requestTypeRepository;

    private static final Map<DocumentStatus, String> STATUS_LABELS = Map.of(
        DocumentStatus.INITIATED, "Brouillon",
        DocumentStatus.SUBMITTED, "Soumise",
        DocumentStatus.VALIDATED_AGENT, "Validée par l'agent",
        DocumentStatus.SIGNED, "Signée",
        DocumentStatus.REJECTED, "Rejetée",
        DocumentStatus.COMPLEMENT_REQUESTED, "Complément demandé",
        DocumentStatus.EXPIRED, "Expirée"
    );

    private static final Map<String, String> TREATMENT_LABELS = Map.of(
        "PENDING_SUBMISSION", "En attente de soumission",
        "AGENT_PROCESSING", "En traitement agent",
        "SIGNATORY_SIGNATURE", "En attente signature",
        "SIGNED", "Signée",
        "REJECTED", "Rejetée",
        "PENDING_CLIENT", "En attente client",
        "EXPIRED", "Expirée"
    );

    public List<RequestTypeMetadataDTO> getAllRequestTypes() {
        return requestTypeRepository.findAll().stream().map(entity -> new RequestTypeMetadataDTO(
            entity.getId(),
            entity.getCode(),
            entity.getLabel())
        ).collect(Collectors.toList());
    }

    public List<StatusMetadataDTO> getAllStatuses() {
        return Arrays.stream(DocumentStatus.values()).map(status -> new StatusMetadataDTO(
            status.name(),
            STATUS_LABELS.getOrDefault(status, status.name()))
        ).collect(Collectors.toList());
    }

    public List<TreatmentMetadataDTO> getAllTreatments() {
        return DocumentStatus.getAllTreatments().stream().map(treatment -> new TreatmentMetadataDTO(
            treatment,
            TREATMENT_LABELS.getOrDefault(treatment, treatment))
        ).collect(Collectors.toList());
    }
}