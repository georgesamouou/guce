package com.guce.application.infrastructure.persistence.repository;

import com.guce.application.infrastructure.persistence.entities.RefAgentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface RefAgentJpaRepository extends JpaRepository<RefAgentEntity, UUID> {
    List<RefAgentEntity> findByRole(String role);

    List<RefAgentEntity> findByIsActiveTrue();
}
