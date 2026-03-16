package com.guce.application.infrastructure.persistence.repository;

import com.guce.application.infrastructure.persistence.entities.FimexRequestEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FimexRequestJpaRepository extends JpaRepository<FimexRequestEntity, UUID> {

        Optional<FimexRequestEntity> findByRefFileNumber(String refFileNumber);

        @Lock(LockModeType.PESSIMISTIC_WRITE)
        @Query("SELECT d FROM FimexRequestEntity d WHERE d.id = :id")
        Optional<FimexRequestEntity> findByIdForUpdate(@Param("id") UUID id);

        @Query("SELECT d FROM FimexRequestEntity d WHERE d.id = :id AND d.client.id = :clientId")
        Optional<FimexRequestEntity> findByIdForClient(@Param("id") UUID id, @Param("clientId") UUID clientId);

        @Query("SELECT d FROM FimexRequestEntity d WHERE d.id = :id AND d.status != 'INITIATED'")
        Optional<FimexRequestEntity> findByIdForFimexAgent(@Param("id") UUID id);

        @Query("SELECT d FROM FimexRequestEntity d WHERE d.id = :id AND d.status NOT IN ('INITIATED', 'SUBMITTED', 'COMPLEMENT_REQUESTED')")
        Optional<FimexRequestEntity> findByIdForFimexSignatory(@Param("id") UUID id);

        @Query("SELECT d FROM FimexRequestEntity d WHERE d.client.id = :clientId AND " +
                "(:search IS NULL OR :search = '' OR " +
                "LOWER(d.refFileNumber) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
                "LOWER(d.client.taxpayerNumber) LIKE LOWER(CONCAT('%', :search, '%'))) AND " +
                "(:status IS NULL OR d.status = :status) AND " +
                "(:treatment IS NULL OR d.status IN :treatmentStatuses) AND " +
                "(:requestTypeId IS NULL OR d.requestType.id = :requestTypeId) AND " +
                "(cast(:fromDate as date) IS NULL OR d.requestedDate >= :fromDate) AND " +
                "(cast(:toDate as date) IS NULL OR d.requestedDate <= :toDate)")
        Page<FimexRequestEntity> searchForClient(
                @Param("search") String search,
                @Param("status") String status,
                @Param("treatment") String treatment,
                @Param("treatmentStatuses") List<String> treatmentStatuses,
                @Param("clientId") UUID clientId,
                @Param("requestTypeId") UUID requestTypeId,
                @Param("fromDate") OffsetDateTime fromDate,
                @Param("toDate") OffsetDateTime toDate,
                Pageable pageable);

        @Query(value = "SELECT d FROM FimexRequestEntity d WHERE d.status != 'INITIATED' AND " +
                "(:search IS NULL OR :search = '' OR " +
                "LOWER(d.refFileNumber) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
                "LOWER(d.client.taxpayerNumber) LIKE LOWER(CONCAT('%', :search, '%'))) AND " +
                "(:status IS NULL OR d.status = :status) AND " +
                "(:treatment IS NULL OR d.status IN :treatmentStatuses) AND " +
                "(:clientId IS NULL OR d.client.id = :clientId) AND " +
                "(:requestTypeId IS NULL OR d.requestType.id = :requestTypeId) AND " +
                "(cast(:fromDate as date) IS NULL OR d.requestedDate >= :fromDate) AND " +
                "(cast(:toDate as date) IS NULL OR d.requestedDate <= :toDate)")
        Page<FimexRequestEntity> searchForFimexAgent(
                @Param("search") String search,
                @Param("status") String status,
                @Param("treatment") String treatment,
                @Param("treatmentStatuses") List<String> treatmentStatuses,
                @Param("clientId") UUID clientId,
                @Param("requestTypeId") UUID requestTypeId,
                @Param("fromDate") OffsetDateTime fromDate,
                @Param("toDate") OffsetDateTime toDate,
                Pageable pageable);

        @Query(value = "SELECT d FROM FimexRequestEntity d WHERE d.status NOT IN ('INITIATED', 'SUBMITTED', 'COMPLEMENT_REQUESTED') AND " +
                "(:search IS NULL OR :search = '' OR " +
                "LOWER(d.refFileNumber) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
                "LOWER(d.client.taxpayerNumber) LIKE LOWER(CONCAT('%', :search, '%'))) AND " +
                "(:status IS NULL OR d.status = :status) AND " +
                "(:treatment IS NULL OR d.status IN :treatmentStatuses) AND " +
                "(:clientId IS NULL OR d.client.id = :clientId) AND " +
                "(:requestTypeId IS NULL OR d.requestType.id = :requestTypeId) AND " +
                "(cast(:fromDate as date) IS NULL OR d.requestedDate >= :fromDate) AND " +
                "(cast(:toDate as date) IS NULL OR d.requestedDate <= :toDate)")
        Page<FimexRequestEntity> searchForFimexSignatory(
                @Param("search") String search,
                @Param("status") String status,
                @Param("treatment") String treatment,
                @Param("treatmentStatuses") List<String> treatmentStatuses,
                @Param("clientId") UUID clientId,
                @Param("requestTypeId") UUID requestTypeId,
                @Param("fromDate") OffsetDateTime fromDate,
                @Param("toDate") OffsetDateTime toDate,
                Pageable pageable);
}