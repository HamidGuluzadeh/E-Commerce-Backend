package com.cybernetics.product_ms.service.impl;

import com.cybernetics.product_ms.dto.request.AddProductRequestDto;
import com.cybernetics.product_ms.dto.request.UpdateProductRequestDto;
import com.cybernetics.product_ms.entity.ProductEntity;
import com.cybernetics.product_ms.exception.ProductNotFoundException;
import com.cybernetics.product_ms.mapper.SellerMapper;
import com.cybernetics.product_ms.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SellerServiceImplTest {
    @Mock
    ProductRepository productRepository;
    @Mock
    SellerMapper sellerMapper;
    @Mock
    SecurityContext securityContext;
    @Mock
    Authentication authentication;

    @InjectMocks
    SellerServiceImpl sellerService;

    AddProductRequestDto addProductRequestDto;
    UpdateProductRequestDto updateProductRequestDto;
    ProductEntity productEntity;
    String username = "seller_user";
    String productId = "123456";

    @BeforeEach
    void setUp() {
        addProductRequestDto = MockData.addProductRequestDto();
        updateProductRequestDto = MockData.updateProductRequestDto();
        productEntity = MockData.productEntity();


        lenient().when(securityContext.getAuthentication()).thenReturn(authentication);
        lenient().when(authentication.getName()).thenReturn(username);
        SecurityContextHolder.setContext(securityContext);
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void testWhenProductIsAddedSuccess() {
        when(sellerMapper.mapProductDtoToEntity(addProductRequestDto)).thenReturn(productEntity);
        when(productRepository.save(productEntity)).thenReturn(productEntity);

        sellerService.addProduct(addProductRequestDto);

        verify(authentication).getName();
        verify(sellerMapper).mapProductDtoToEntity(addProductRequestDto);
        verify(productRepository).save(productEntity);
    }

    @Test
    void testWhenProductIsUpdatedSuccess() {
        when(productRepository.findByProductId(productId)).thenReturn(Optional.of(productEntity));
        when(sellerMapper.mapUpdateProductDtoToEntity(updateProductRequestDto, productEntity)).thenReturn(productEntity);

        sellerService.updateProduct(updateProductRequestDto, productId);

        verify(authentication).getName();
        verify(productRepository).findByProductId(productId);
        verify(sellerMapper).mapUpdateProductDtoToEntity(updateProductRequestDto, productEntity);
    }

    @Test
    void testWhenProductIsNotFoundForUpdate() {
        when(productRepository.findByProductId(productId)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> sellerService.updateProduct(updateProductRequestDto, productId));

        verify(sellerMapper, never()).mapUpdateProductDtoToEntity(any(), any());
    }

    @Test
    void testWhenProductOwnerMismatchForUpdate() {
        productEntity.setUsername("other_user");

        when(productRepository.findByProductId(productId)).thenReturn(Optional.of(productEntity));

        sellerService.updateProduct(updateProductRequestDto, productId);

        verify(sellerMapper, never()).mapUpdateProductDtoToEntity(any(), any());
    }

    @Test
    void testWhenProductIsDeletedSuccess() {
        when(productRepository.existsByProductId(productId)).thenReturn(true);

        sellerService.deleteProduct(productId);

        verify(productRepository).existsByProductId(productId);
        verify(productRepository).deleteById(productId);
    }

    @Test
    void testWhenProductIsNotFoundForDelete() {
        when(productRepository.existsByProductId(productId)).thenReturn(false);

        assertThrows(ProductNotFoundException.class, () -> sellerService.deleteProduct(productId));

        verify(productRepository, never()).deleteById(anyString());
    }
}