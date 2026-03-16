package com.guce.application.infrastructure.persistence.repository;

import com.guce.application.infrastructure.persistence.entities.FimexAttachmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface FimexAttachmentJpaRepository extends JpaRepository<FimexAttachmentEntity, UUID> {
    List<FimexAttachmentEntity> findByRequestId(UUID requestId);
}
