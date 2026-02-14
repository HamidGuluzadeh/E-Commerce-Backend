package com.cybernetics.product_ms.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record UpdateProductRequestDto(@Size(max = 150, message = "Product name cannot contain more than 150 characters!")
                                      String productName,
                                      String description,
                                      Long categoryId,
                                      @Min(value = 1, message = "Stock count must be at least 1 !")
                                      Integer stock,
                                      @Max(value = 10000, message = "Price cannot be higher than 10000!")
                                      BigDecimal price) {
}
