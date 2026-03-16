package com.guce.application.adapter.out.persistence;

import com.guce.application.domain.model.FimexRequestModel;
import com.guce.application.domain.port.out.FimexRequestRepositoryOutPort;
import com.guce.application.adapter.out.persistence.mapper.FimexRequestPersistenceMapper;
import com.guce.application.infrastructure.persistence.repository.FimexRequestJpaRepository;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FimexRequestRepositoryAdapter implements FimexRequestRepositoryOutPort {

    private final FimexRequestJpaRepository repository;
    private final FimexRequestPersistenceMapper mapper;

    @Override
    public Optional<FimexRequestModel> findById(UUID id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<FimexRequestModel> findByIdForUpdate(UUID id) {
        return repository.findByIdForUpdate(id).map(mapper::toDomain);
    }

    @Override
    public FimexRequestModel save(FimexRequestModel model) {
        var entity = mapper.toEntity(model);
        var saved = repository.save(entity);
        return mapper.toDomain(saved);
    }
}
