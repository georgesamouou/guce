package com.guce.application.infrastructure.persistence.repository;

import com.guce.application.infrastructure.persistence.entities.StatusHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface StatusHistoryJpaRepository extends JpaRepository<StatusHistoryEntity, UUID> {
    List<StatusHistoryEntity> findByRequestIdOrderByCreatedAtDesc(UUID requestId);
}
