package com.guce.application.infrastructure.persistence.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ref_client")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefClientEntity {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "taxpayer_number", nullable = false, unique = true)
    private String taxpayerNumber;

    @Column(name = "company_name")
    private String companyName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "office_id")
    private RefOfficeEntity office;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id")
    private FimexOrganizationEntity organization;
}
