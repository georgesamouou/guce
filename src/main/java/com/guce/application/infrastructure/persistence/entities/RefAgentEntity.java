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
@Table(name = "ref_agent")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefAgentEntity {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "email")
    private String email;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "title")
    private String title;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;
}
