package com.cybernetics.payment_ms.service.impl;

import com.cybernetics.payment_ms.dto.request.PaymentRequestDto;
import com.cybernetics.payment_ms.entity.PaymentHistoryEntity;

import java.math.BigDecimal;

public class MockData {
    public static PaymentRequestDto paymentRequestDto() {
        return PaymentRequestDto.builder()
                .productId("productId")
                .quantity(10)
                .price(BigDecimal.valueOf(100))
                .build();
    }

    public static PaymentHistoryEntity paymentHistoryEntity() {
        return PaymentHistoryEntity.builder()
                .quantity(10)
                .price(100L)
                .status("SUCCESS")
                .azericardStatus("1")
                .build();
    }
}
