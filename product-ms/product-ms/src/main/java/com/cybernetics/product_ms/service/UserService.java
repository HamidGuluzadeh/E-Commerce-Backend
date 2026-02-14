package com.cybernetics.product_ms.service;

import com.cybernetics.product_ms.dto.request.DecreaseCountRequestDto;
import com.cybernetics.product_ms.dto.response.GetStockPriceResponseDto;
import com.cybernetics.product_ms.dto.response.ProductResponseDto;

import java.util.List;

public interface UserService {

    List<ProductResponseDto> getAllProducts();

    ProductResponseDto getProductById(String productId);

    void decreaseProductCount(DecreaseCountRequestDto decreaseCountRequestDto);

    GetStockPriceResponseDto getStockAndPrice(String productId);

}
