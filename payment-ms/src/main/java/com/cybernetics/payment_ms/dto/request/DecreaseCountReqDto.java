package com.cybernetics.payment_ms.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record DecreaseCountReqDto(
        @NotBlank(message = "productId is null!") String productId,
        @NotNull(message = "count is null!") Integer count
) {
}
