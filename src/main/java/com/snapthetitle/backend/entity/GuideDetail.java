package com.snapthetitle.backend.entity;

import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "GUIDE_DETAILS")
public class GuideDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GUIDE_ID", nullable = false)
    private Guide guide;

    @Column(name = "SUBTITLE", nullable = false, length = 100)
    private String subtitle;

    @Column(name = "DESCRIPTION", columnDefinition = "TEXT", nullable = false)
    private String description;

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
