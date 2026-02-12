package com.cybernetics.product_ms.service.impl;

import com.cybernetics.product_ms.dto.request.AddProductRequestDto;
import com.cybernetics.product_ms.dto.request.UpdateProductRequestDto;
import com.cybernetics.product_ms.entity.ProductEntity;
import com.cybernetics.product_ms.exception.ProductNotFoundException;
import com.cybernetics.product_ms.mapper.SellerMapper;
import com.cybernetics.product_ms.repository.ProductRepository;
import com.cybernetics.product_ms.service.SellerService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SellerServiceImpl implements SellerService {
    ProductRepository productRepository;
    SellerMapper sellerMapper;

    @Override
    @Transactional
    public void addProduct(AddProductRequestDto addProductRequestDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        ProductEntity productEntity = sellerMapper.mapProductDtoToEntity(addProductRequestDto);
        productEntity.setUsername(username);

        productRepository.save(productEntity);
    }

    @Override
    @Transactional
    public void updateProduct(UpdateProductRequestDto updateProductRequestDto, String productId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        ProductEntity productEntity = productRepository.findByProductId(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found!"));

        if (productEntity.getUsername().equals(username)) {
            sellerMapper.mapUpdateProductDtoToEntity(updateProductRequestDto, productEntity);
            productEntity.setUpdatedAt(Instant.now());
        }
    }

    @Override
    @Transactional
    public void deleteProduct(String productId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        if (!productRepository.existsByProductId(productId)) {
            throw new ProductNotFoundException("Product not found!");
        }

        productRepository.deleteById(productId);
    }
}
