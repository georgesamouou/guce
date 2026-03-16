package com.guce.application.infrastructure.persistence.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "fimex_request")
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FimexRequestEntity {

    @Id
    @Column(name = "id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private RefClientEntity client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_type_id", nullable = false)
    private RefRequestTypeEntity requestType;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "is_renewal", nullable = false)
    private boolean isRenewal;

    @Column(name = "ref_file_number")
    private String refFileNumber;

    @Column(name = "requested_date", nullable = false)
    private OffsetDateTime requestedDate;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agent_id")
    private RefAgentEntity agent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "signatory_id")
    private RefAgentEntity signatory;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private OffsetDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @OneToMany(mappedBy = "request", cascade = { CascadeType.PERSIST,
            CascadeType.MERGE }, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<FimexProductEntity> products = new ArrayList<>();

    @OneToMany(mappedBy = "requestId", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
    private List<FimexAttachmentEntity> attachments = new ArrayList<>();
}
