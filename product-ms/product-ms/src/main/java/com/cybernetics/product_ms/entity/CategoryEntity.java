package com.cybernetics.product_ms.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table(name = "categories")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id", nullable = false, updatable = false)
    Long categoryId;

    @Column(name = "category_name", nullable = false, unique = true)
    String categoryName;

    @Column(name = "created_at", nullable = false, updatable = false, insertable = false)
    @CreationTimestamp
    Instant createdAt;

    @Column(name = "updated_at", insertable = false)
    Instant updatedAt;
}
