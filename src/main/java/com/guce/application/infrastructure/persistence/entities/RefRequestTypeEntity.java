package com.guce.application.infrastructure.persistence.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ref_request_type")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefRequestTypeEntity {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Column(name = "label", nullable = false)
    private String label;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;
}
