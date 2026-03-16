package com.guce.application.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.guce.application.infrastructure.persistence.entities.FimexRequestEntity;
import java.util.UUID;

@Repository
public interface FimexSequenceJpaRepository extends JpaRepository<FimexRequestEntity, UUID> {

    @Query(value = "SELECT nextval('fimex_ref_number_seq')", nativeQuery = true)
    Long getNextReferenceNumber();
}
