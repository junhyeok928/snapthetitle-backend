package com.snapthetitle.backend.entity;

import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "PARTNERS")
public class Partner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "CATEGORY", nullable = false, length = 50)
    private String category;

    @Column(name = "NAME", nullable = false, length = 100)
    private String name;

    @Column(name = "INSTAGRAM", nullable = false, length = 100)
    private String instagram;

    @Column(name = "LINK_URL", nullable = false, length = 255)
    private String linkUrl;

    @Column(name = "DISPLAY_ORDER", nullable = false)
    private Integer displayOrder;

    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "DELETED_YN", nullable = false, length = 1)
    private String deletedYn;

    // getters/setters
}
