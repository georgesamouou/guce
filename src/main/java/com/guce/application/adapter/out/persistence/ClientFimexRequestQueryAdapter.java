package com.guce.application.adapter.out.persistence;

import com.guce.application.adapter.out.persistence.mapper.FimexRequestPersistenceMapper;
import com.guce.application.domain.model.DocumentStatus;
import com.guce.application.domain.model.FimexRequestModel;
import com.guce.application.domain.model.FimexRequestSummary;
import com.guce.application.domain.model.SearchCriteria;
import com.guce.application.domain.port.out.ClientFimexRequestQueryOutPort;
import com.guce.application.infrastructure.persistence.repository.FimexRequestJpaRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClientFimexRequestQueryAdapter implements ClientFimexRequestQueryOutPort {

    private final FimexRequestJpaRepository repository;
    private final FimexRequestPersistenceMapper mapper;

    @Override
    public Optional<FimexRequestModel> findByIdForClient(UUID id, UUID clientId) {
        return repository.findByIdForClient(id, clientId)
                .map(mapper::toDomain);
    }

    @Override
    public Page<FimexRequestSummary> searchForClient(SearchCriteria criteria) {
        Pageable pageable = PageRequest.of(criteria.page(), criteria.size(), Sort.by("createdAt").descending());

        // Convertir le traitement en liste de statuts si nécessaire
        List<String> treatmentStatuses = new ArrayList<>();
        if (criteria.treatment() != null) {
            DocumentStatus status = DocumentStatus.fromTreatment(criteria.treatment());
            if (status != null) {
                treatmentStatuses.add(status.name());
            }
        }

        return repository.searchForClient(
                criteria.search(),
                criteria.status(),
                criteria.treatment(),
                treatmentStatuses,
                criteria.clientId(),
                criteria.requestTypeId(),
                criteria.fromDate(),
                criteria.toDate(),
                pageable).map(mapper::toSummary);
    }
}