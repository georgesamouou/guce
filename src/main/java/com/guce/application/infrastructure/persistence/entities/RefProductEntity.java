package com.guce.application.infrastructure.persistence.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ref_product")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefProductEntity {

    @Id
    @Column(name = "code_sh")
    private String codeSh;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<FimexProductEntity> fimexProducts = new ArrayList<>();
}
