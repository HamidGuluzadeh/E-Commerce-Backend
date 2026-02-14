package com.cybernetics.product_ms.service.impl;

import com.cybernetics.product_ms.dto.request.DecreaseCountRequestDto;
import com.cybernetics.product_ms.dto.response.GetStockPriceResponseDto;
import com.cybernetics.product_ms.dto.response.ProductResponseDto;
import com.cybernetics.product_ms.entity.ProductEntity;
import com.cybernetics.product_ms.exception.NotEnoughProductException;
import com.cybernetics.product_ms.exception.ProductNotFoundException;
import com.cybernetics.product_ms.mapper.UserMapper;
import com.cybernetics.product_ms.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    ProductRepository productRepository;
    @Mock
    UserMapper userMapper;

    @InjectMocks
    UserServiceImpl userService;

    String productId = "123456";
    DecreaseCountRequestDto decreaseCountRequestDto;
    DecreaseCountRequestDto largeCountRequestDto;
    ProductEntity productEntity;
    ProductResponseDto productResponseDto;
    List<ProductEntity> productEntities;

    @BeforeEach
    void setUp() {
        decreaseCountRequestDto = MockData.decreaseCountRequestDto();
        largeCountRequestDto = MockData.largeCountRequestDto();
        productEntity = MockData.productEntity();
        productResponseDto = MockData.productResponseDto();
        productEntities = MockData.getProductEntityList();
    }

    @Test
    void testWhenGetAllProductsSuccess() {
        when(productRepository.findAll()).thenReturn(productEntities);
        when(userMapper.mapProductEntityToResponseDto(any(ProductEntity.class))).thenReturn(productResponseDto);

        List<ProductResponseDto> productResponseDtoList = userService.getAllProducts();

        assertEquals(2, productResponseDtoList.size());

        verify(productRepository).findAll();
        verify(userMapper, times(2)).mapProductEntityToResponseDto(any(ProductEntity.class));
    }

    @Test
    void testWhenProductIsFoundByIdSuccess() {
        when(productRepository.findByProductId(productId)).thenReturn(Optional.of(productEntity));
        when(userMapper.mapProductEntityToResponseDto(productEntity)).thenReturn(productResponseDto);

        ProductResponseDto result = userService.getProductById(productId);

        assertNotNull(result);
        assertEquals(productResponseDto, result);

        verify(productRepository).findByProductId(productId);
        verify(userMapper).mapProductEntityToResponseDto(productEntity);
    }

    @Test
    void testWhenProductIsNotFoundForGet() {
        when(productRepository.findByProductId(productId)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> userService.getProductById(productId));

        verify(productRepository).findByProductId(anyString());
    }

    @Test
    void testWhenProductCountIsDecreasedSuccess() {
        when(productRepository.findByProductId(productId)).thenReturn(Optional.of(productEntity));

        userService.decreaseProductCount(decreaseCountRequestDto);

        assertEquals(8, productEntity.getStock());

        verify(productRepository).findByProductId(productId);
    }

    @Test
    void testWhenProductCountIsNotFoundForDecrease() {
        when(productRepository.findByProductId(productId)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> userService.decreaseProductCount(decreaseCountRequestDto));

        verify(productRepository).findByProductId(anyString());
    }

    @Test
    void testWhenProductStockIsInsufficient() {
        when(productRepository.findByProductId(largeCountRequestDto.productId())).thenReturn(Optional.of(productEntity));

        assertThrows(NotEnoughProductException.class, () -> userService.decreaseProductCount(largeCountRequestDto));

        verify(productRepository).findByProductId(anyString());
    }

    @Test
    void testWhenGetProductStockAndPriceSuccess() {
        when(productRepository.findByProductId(productId)).thenReturn(Optional.of(productEntity));

        GetStockPriceResponseDto result = userService.getStockAndPrice(productId);

        assertNotNull(result);
        assertEquals(productEntity.getStock(), result.stock());
        assertEquals(productEntity.getPrice(), result.price());

        verify(productRepository).findByProductId(productId);
    }

    @Test
    void testWhenProductIsNotFoundForStockAndPrice() {
        when(productRepository.findByProductId(productId)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> userService.getStockAndPrice(productId));

        verify(productRepository).findByProductId(productId);
    }
}