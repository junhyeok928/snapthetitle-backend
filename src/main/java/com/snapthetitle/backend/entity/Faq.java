package com.snapthetitle.backend.entity;

import lombok.Data;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "FAQS")
public class Faq {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "CATEGORY", nullable = false, length = 50)
    private String category;

    @Column(name = "QUESTION", columnDefinition = "TEXT", nullable = false)
    private String question;

    @Column(name = "ANSWER", columnDefinition = "TEXT", nullable = false)
    private String answer;

    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "DELETED_YN", nullable = false, length = 1)
    private String deletedYn;

    // getters/setters
}
