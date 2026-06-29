package com.cybernetics.payment_ms.service.impl;

import com.cybernetics.payment_ms.dto.request.PaymentRequestDto;
import com.cybernetics.payment_ms.dto.response.AzericardResponseDto;
import com.cybernetics.payment_ms.dto.response.GetStockPriceResponseDto;
import com.cybernetics.payment_ms.entity.PaymentHistoryEntity;

import java.math.BigDecimal;

public class MockData {
    public static final String USER_EMAIL = "test@example.com";
    public static final String USERNAME = "testUser";

    public static PaymentRequestDto paymentRequestDto() {
        return PaymentRequestDto.builder()
                .productId("productId")
                .quantity(10)
                .build();
    }

    public static PaymentHistoryEntity paymentHistoryEntity() {
        return PaymentHistoryEntity.builder()
                .quantity(10)
                .status("SUCCESS")
                .azericardStatus("1")
                .build();
    }

    public static GetStockPriceResponseDto stockPriceResponse(int stock) {
        return GetStockPriceResponseDto.builder()
                .stock(stock)
                .price(BigDecimal.valueOf(100.0))
                .build();
    }

    public static AzericardResponseDto azericardResponse(String status) {
        return AzericardResponseDto.builder()
                .orderId("orderId")
                .status(status)
                .build();
    }
}
