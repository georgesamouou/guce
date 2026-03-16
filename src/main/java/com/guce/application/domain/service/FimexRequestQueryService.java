package com.guce.application.domain.service;

import com.guce.application.domain.annotation.DomainService;
import com.guce.application.domain.exception.ResourceNotFoundException;
import com.guce.application.domain.model.DocumentStatus;
import com.guce.application.domain.model.FimexRequestModel;
import com.guce.application.domain.model.FimexRequestSummary;
import com.guce.application.domain.model.SearchCriteria;
import com.guce.application.domain.port.out.AgentFimexRequestQueryOutPort;
import com.guce.application.domain.port.out.ClientFimexRequestQueryOutPort;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

@DomainService
@RequiredArgsConstructor
public class FimexRequestQueryService {

    private final ClientFimexRequestQueryOutPort clientQueryOutPort;
    private final AgentFimexRequestQueryOutPort agentQueryOutPort;

    public FimexRequestModel findByIdForClient(UUID id, UUID clientId) {
        return clientQueryOutPort.findByIdForClient(id, clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Request not found: " + id));
    }

    public FimexRequestModel findByIdForAgent(UUID id) {
        return agentQueryOutPort.findByIdForFimexAgent(id)
                .orElseThrow(() -> new ResourceNotFoundException("Request not found: " + id));
    }

    public FimexRequestModel findByIdForSignatory(UUID id) {
        return agentQueryOutPort.findByIdForFimexSignatory(id)
                .orElseThrow(() -> new ResourceNotFoundException("Request not found: " + id));
    }

    public Page<FimexRequestSummary> searchForClient(SearchCriteria criteria) {
        // Convertir le traitement en liste de statuts si nécessaire
        if (criteria.treatment() != null && criteria.status() == null) {
            DocumentStatus status = DocumentStatus.fromTreatment(criteria.treatment());
            if (status != null) {
                // On remplace le traitement par le statut correspondant
                SearchCriteria updatedCriteria = new SearchCriteria(
                        criteria.search(),
                        status.name(),
                        null,
                        criteria.clientId(),
                        criteria.requestTypeId(),
                        criteria.fromDate(),
                        criteria.toDate(),
                        criteria.page(),
                        criteria.size()
                );
                return clientQueryOutPort.searchForClient(updatedCriteria);
            }
        }
        return clientQueryOutPort.searchForClient(criteria);
    }

    public Page<FimexRequestSummary> searchForAgent(SearchCriteria criteria) {
        // Convertir le traitement en liste de statuts si nécessaire
        if (criteria.treatment() != null && criteria.status() == null) {
            DocumentStatus status = DocumentStatus.fromTreatment(criteria.treatment());
            if (status != null) {
                SearchCriteria updatedCriteria = new SearchCriteria(
                        criteria.search(),
                        status.name(),
                        null,
                        criteria.clientId(),
                        criteria.requestTypeId(),
                        criteria.fromDate(),
                        criteria.toDate(),
                        criteria.page(),
                        criteria.size()
                );
                return agentQueryOutPort.searchForFimexAgent(updatedCriteria);
            }
        }
        return agentQueryOutPort.searchForFimexAgent(criteria);
    }

    public Page<FimexRequestSummary> searchForSignatory(SearchCriteria criteria) {
        // Convertir le traitement en liste de statuts si nécessaire
        if (criteria.treatment() != null && criteria.status() == null) {
            DocumentStatus status = DocumentStatus.fromTreatment(criteria.treatment());
            if (status != null) {
                SearchCriteria updatedCriteria = new SearchCriteria(
                        criteria.search(),
                        status.name(),
                        null,
                        criteria.clientId(),
                        criteria.requestTypeId(),
                        criteria.fromDate(),
                        criteria.toDate(),
                        criteria.page(),
                        criteria.size()
                );
                return agentQueryOutPort.searchForFimexSignatory(updatedCriteria);
            }
        }
        return agentQueryOutPort.searchForFimexSignatory(criteria);
    }
}