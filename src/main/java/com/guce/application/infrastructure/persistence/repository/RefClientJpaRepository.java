package com.guce.application.infrastructure.persistence.repository;

import com.guce.application.infrastructure.persistence.entities.RefClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface RefClientJpaRepository extends JpaRepository<RefClientEntity, UUID> {
}
