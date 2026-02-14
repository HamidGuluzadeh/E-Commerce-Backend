package com.cybernetics.product_ms.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record DecreaseCountRequestDto(@NotBlank(message = "productId is null!") String productId,
                                      @NotNull(message = "count is null!") Integer count) {
}
