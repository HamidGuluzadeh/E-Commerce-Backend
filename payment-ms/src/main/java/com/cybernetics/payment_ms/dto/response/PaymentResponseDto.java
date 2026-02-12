package com.cybernetics.payment_ms.dto.response;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record PaymentResponseDto(String orderId,
                                 String status,
                                 BigDecimal totalAmount) {
}
