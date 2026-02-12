package com.cybernetics.product_ms.service;

import com.cybernetics.product_ms.dto.request.DecreaseCountRequestDto;
import com.cybernetics.product_ms.dto.response.ProductCountResponseDto;
import com.cybernetics.product_ms.dto.response.ProductResponseDto;

import java.math.BigDecimal;
import java.util.List;

public interface UserService {

    List<ProductResponseDto> getAllProducts();

    ProductResponseDto getProductById(String productId);

    Integer getProductCount(String productId);

    void decreaseProductCount(DecreaseCountRequestDto decreaseCountRequestDto);

    BigDecimal getProductPrice(String productId);

}
