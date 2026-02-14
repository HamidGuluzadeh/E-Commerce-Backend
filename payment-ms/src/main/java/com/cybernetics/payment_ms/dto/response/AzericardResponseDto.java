package com.cybernetics.payment_ms.dto.response;

import lombok.Builder;

@Builder
public record AzericardResponseDto(String orderId,
                                   String status) {

}
