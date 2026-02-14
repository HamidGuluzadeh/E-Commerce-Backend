package com.cybernetics.product_ms.dto.response;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record GetStockPriceResponseDto(Integer stock,
                                       BigDecimal price) {
}
