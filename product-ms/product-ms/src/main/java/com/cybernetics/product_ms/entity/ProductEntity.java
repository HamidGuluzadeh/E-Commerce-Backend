package com.cybernetics.product_ms.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "products")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "product_id", length = 36, updatable = false, nullable = false)
    String productId;

    @Column(name = "product_name", length = 150, nullable = false)
    String productName;

    @Column(name = "description")
    String description;

    @Column(name = "username", length = 30, nullable = false)
    String username;

    @Column(name = "stock", nullable = false)
    Integer stock;

    @Column(name = "price", nullable = false, precision = 6, scale = 2)
    BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id",  nullable = false)
    CategoryEntity category;

    @Column(name = "created_at", nullable = false, updatable = false, insertable = false)
    @CreationTimestamp
    Instant createdAt;

    @Column(name = "updated_at", insertable = false)
    Instant updatedAt;
}
