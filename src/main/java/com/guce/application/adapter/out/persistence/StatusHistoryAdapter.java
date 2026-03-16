package com.guce.application.adapter.out.persistence;

import com.guce.application.domain.model.StatusHistoryModel;
import com.guce.application.domain.port.out.StatusHistoryOutPort;
import com.guce.application.adapter.out.persistence.mapper.StatusHistoryPersistenceMapper;
import com.guce.application.infrastructure.persistence.repository.StatusHistoryJpaRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class StatusHistoryAdapter implements StatusHistoryOutPort {

    private final StatusHistoryJpaRepository repository;
    private final StatusHistoryPersistenceMapper mapper;

    @Override
    public StatusHistoryModel save(StatusHistoryModel history) {
        var entity = mapper.toEntity(history);
        var saved = repository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public List<StatusHistoryModel> findByRequestId(UUID requestId) {
        return repository.findByRequestIdOrderByCreatedAtDesc(requestId)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
}
