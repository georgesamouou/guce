package com.guce.application.infrastructure.persistence.entities;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "fimex_attachment")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FimexAttachmentEntity {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "request_id", nullable = false)
    private UUID requestId;

    @Column(name = "attachment_type", nullable = false)
    private String attachmentType;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @Column(name = "content_type", nullable = false)
    private String contentType;

    @Basic(fetch = FetchType.LAZY)
    @Column(name = "content", nullable = false)
    private byte[] content;

    @Column(name = "file_status", nullable = false)
    private String fileStatus;

    @Column(name = "upload_date", nullable = false)
    private OffsetDateTime uploadDate;
}
