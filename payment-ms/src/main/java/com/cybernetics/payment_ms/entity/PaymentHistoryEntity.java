package com.cybernetics.payment_ms.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment_history")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    String id;

    @Column(name = "product_id", nullable = false, length = 100)
    String productId;

    @Column(name = "quantity", nullable = false)
    Integer quantity;

    @Column(name = "total_amount", nullable = false)
    BigDecimal totalAmount;

    @Column(name = "order_id", length = 100)
    String orderId;

    @Column(name = "status", nullable = false, length = 100)
    String status;

    @Column(name = "azericard_status", length = 100)
    String azericardStatus;

    @Column(name = "created_at", updatable = false, nullable = false, insertable = false)
    @CreationTimestamp
    LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false)
    @UpdateTimestamp
    LocalDateTime updatedAt;

}
