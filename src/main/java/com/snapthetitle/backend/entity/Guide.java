package com.snapthetitle.backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "GUIDES")
public class Guide {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "CATEGORY", nullable = false, length = 50)
    private String category;

    @Column(name = "CONTENT", columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(name = "LINK_TEXT", length = 100)
    private String linkText;

    @Column(name = "LINK_URL", length = 255)
    private String linkUrl;

    @Column(name = "DISPLAY_ORDER", nullable = false)
    private Integer displayOrder;

    @OneToMany(mappedBy = "guide", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GuideDetail> details;

    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "DELETED_YN", nullable = false, length = 1)
    private String deletedYn;

    // getters/setters
}
