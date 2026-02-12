package com.cybernetics.product_ms.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductResponseDto {
     String productName;
     String description;
     Long categoryId;
     String username;
     Integer stock;
     BigDecimal price;
}
