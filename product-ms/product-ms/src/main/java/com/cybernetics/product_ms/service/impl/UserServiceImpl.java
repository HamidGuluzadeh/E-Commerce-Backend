package com.cybernetics.product_ms.service.impl;

import com.cybernetics.product_ms.dto.request.DecreaseCountRequestDto;
import com.cybernetics.product_ms.dto.response.GetStockPriceResponseDto;
import com.cybernetics.product_ms.dto.response.ProductResponseDto;
import com.cybernetics.product_ms.entity.ProductEntity;
import com.cybernetics.product_ms.exception.NotEnoughProductException;
import com.cybernetics.product_ms.exception.ProductNotFoundException;
import com.cybernetics.product_ms.mapper.UserMapper;
import com.cybernetics.product_ms.repository.ProductRepository;
import com.cybernetics.product_ms.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {
    ProductRepository productRepository;
    UserMapper userMapper;

    @Override
    public List<ProductResponseDto> getAllProducts() {
        List<ProductEntity> productEntities = productRepository.findAll();

        List<ProductResponseDto> productResponseDtoList = new ArrayList<>();

        for (ProductEntity productEntity : productEntities) {
            productResponseDtoList.add(userMapper.mapProductEntityToResponseDto(productEntity));
        }

        return productResponseDtoList;
    }

    @Override
    public ProductResponseDto getProductById(String productId) {
        ProductEntity productEntity = productRepository.findByProductId(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found!"));

        return userMapper.mapProductEntityToResponseDto(productEntity);
    }

    @Override
    @Transactional
    public void decreaseProductCount(DecreaseCountRequestDto decreaseCountRequestDto) {
        ProductEntity productEntity = productRepository.findByProductId(decreaseCountRequestDto.productId())
                .orElseThrow(() -> new ProductNotFoundException("Product not found!"));

        if (productEntity.getStock() < decreaseCountRequestDto.count()) {
            throw new NotEnoughProductException("Product stock is less than requested count!");
        }

        productEntity.setStock(productEntity.getStock() - decreaseCountRequestDto.count());
    }

    @Override
    public GetStockPriceResponseDto getStockAndPrice(String productId) {
        ProductEntity productEntity = productRepository.findByProductId(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found!"));

        return GetStockPriceResponseDto.builder()
                .stock(productEntity.getStock())
                .price(productEntity.getPrice())
                .build();
    }
}
