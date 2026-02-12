package com.cybernetics.payment_ms.dto.request;

import lombok.Builder;

@Builder
public record PaymentRequestDto(String productId,
                                Integer quantity) {
}
