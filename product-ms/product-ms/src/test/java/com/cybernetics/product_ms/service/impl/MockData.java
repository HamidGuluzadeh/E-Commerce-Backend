package com.cybernetics.product_ms.service.impl;

import com.cybernetics.product_ms.dto.request.AddProductRequestDto;
import com.cybernetics.product_ms.dto.request.DecreaseCountRequestDto;
import com.cybernetics.product_ms.dto.request.UpdateProductRequestDto;
import com.cybernetics.product_ms.dto.response.ProductResponseDto;
import com.cybernetics.product_ms.entity.ProductEntity;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

public class MockData {
    public static AddProductRequestDto addProductRequestDto() {
        return AddProductRequestDto.builder()
                .productName("productName")
                .description("description")
                .categoryId(1L)
                .stock(10)
                .price(BigDecimal.valueOf(1000))
                .build();
    }

    public static UpdateProductRequestDto updateProductRequestDto() {
        return UpdateProductRequestDto.builder()
                .productName("productName")
                .description("description")
                .categoryId(1L)
                .stock(10)
                .price(BigDecimal.valueOf(1000))
                .build();
    }

    public static ProductEntity productEntity() {
        return ProductEntity.builder()
                .productId("123456")
                .productName("productName")
                .description("description")
                .price(BigDecimal.valueOf(1000))
                .stock(10)
                .username("seller_user")
                .createdAt(Instant.now())
                .build();
    }

    public static ProductResponseDto productResponseDto() {
        return ProductResponseDto.builder()
                .productName("productName")
                .description("description")
                .categoryId(1L)
                .stock(10)
                .price(BigDecimal.valueOf(1000))
                .build();
    }

    public static DecreaseCountRequestDto decreaseCountRequestDto() {
        return DecreaseCountRequestDto.builder()
                .productId("123456")
                .count(2)
                .build();
    }

    public static DecreaseCountRequestDto largeCountRequestDto() {
        return DecreaseCountRequestDto.builder()
                .productId("123456")
                .count(20)
                .build();
    }

    public static List<ProductEntity> getProductEntityList() {
        return Arrays.asList(productEntity(), productEntity());
    }
}
