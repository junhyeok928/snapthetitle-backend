// src/main/java/com/snapthetitle/backend/entity/Product.java
package com.snapthetitle.backend.entity;

import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "PRODUCTS")
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "YEAR", nullable = false)
    private Integer year;

    @Column(name = "NAME", nullable = false, length = 100)
    private String name;

    @Column(name = "DESCRIPTION", columnDefinition = "TEXT")
    private String description;

    @Column(name = "PRICE", nullable = false, length = 50)
    private String price;

    @Column(name = "IMAGE_URL", length = 512)
    private String imageUrl;

    // ← 아래 필드 추가
    @Column(name = "DISPLAY_ORDER", nullable = false)
    private Integer displayOrder;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductOption> options;

    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "DELETED_YN", nullable = false, length = 1)
    private String deletedYn;
}
