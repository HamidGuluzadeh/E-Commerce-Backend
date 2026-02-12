package com.cybernetics.payment_ms.repository;

import com.cybernetics.payment_ms.entity.PaymentHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentHistoryRepository extends JpaRepository<PaymentHistoryEntity, Long> {
}
