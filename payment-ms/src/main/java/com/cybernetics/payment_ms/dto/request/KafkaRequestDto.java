package com.cybernetics.payment_ms.dto.request;

import lombok.Builder;

@Builder
public record KafkaRequestDto(String email,
                              String orderId,
                              String username) {
}
