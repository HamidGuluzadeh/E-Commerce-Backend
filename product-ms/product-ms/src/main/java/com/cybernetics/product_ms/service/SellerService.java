package com.cybernetics.product_ms.service;

import com.cybernetics.product_ms.dto.request.AddProductRequestDto;
import com.cybernetics.product_ms.dto.request.UpdateProductRequestDto;


public interface SellerService {

    void addProduct(AddProductRequestDto addProductRequestDto);

    void updateProduct(UpdateProductRequestDto updateProductRequestDto, String id);

    void deleteProduct(String productId);

}
