package com.snapthetitle.backend.entity;

import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "ATTACHMENTS")
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "ENTITY_TYPE", nullable = false, length = 50)
    private String entityType;

    @Column(name = "ENTITY_ID", nullable = false)
    private Long entityId;

    @Column(name = "FILE_URL", nullable = false, length = 512)
    private String fileUrl;

    @Column(name = "ORIGINAL_NAME", length = 255)
    private String originalName;

    @Column(name = "MIME_TYPE", length = 100)
    private String mimeType;

    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "DELETED_YN", nullable = false, length = 1)
    private String deletedYn;

    // getters/setters
}
