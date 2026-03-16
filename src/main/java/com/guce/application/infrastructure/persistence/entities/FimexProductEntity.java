package com.guce.application.infrastructure.persistence.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "fimex_product")
@IdClass(FimexProductId.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FimexProductEntity {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id", nullable = false)
    private FimexRequestEntity request;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_sh", referencedColumnName = "code_sh", nullable = false)
    private RefProductEntity product;
}
