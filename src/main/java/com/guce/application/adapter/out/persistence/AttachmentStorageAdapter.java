package com.guce.application.adapter.out.persistence;

import com.guce.application.domain.model.FimexAttachmentModel;
import com.guce.application.domain.port.out.AttachmentStorageOutPort;
import com.guce.application.adapter.out.persistence.mapper.FimexAttachmentPersistenceMapper;
import com.guce.application.infrastructure.persistence.repository.FimexAttachmentJpaRepository;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AttachmentStorageAdapter implements AttachmentStorageOutPort {

    private final FimexAttachmentJpaRepository repository;
    private final FimexAttachmentPersistenceMapper mapper;

    @Override
    public FimexAttachmentModel save(FimexAttachmentModel attachment) {
        var entity = mapper.toEntity(attachment);
        var saved = repository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<FimexAttachmentModel> findById(UUID attachmentId) {
        return repository.findById(attachmentId).map(mapper::toDomain);
    }

    @Override
    public void deleteById(UUID attachmentId) {
        repository.deleteById(attachmentId);
    }
}
