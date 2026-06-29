package com.cybernetics.payment_ms.service.impl;

import com.cybernetics.payment_ms.dto.request.DecreaseCountReqDto;
import com.cybernetics.payment_ms.dto.request.KafkaRequestDto;
import com.cybernetics.payment_ms.dto.request.PaymentRequestDto;
import com.cybernetics.payment_ms.dto.response.AzericardResponseDto;
import com.cybernetics.payment_ms.dto.response.GetStockPriceResponseDto;
import com.cybernetics.payment_ms.dto.response.PaymentResponseDto;
import com.cybernetics.payment_ms.entity.PaymentHistoryEntity;
import com.cybernetics.payment_ms.exception.AzericardFailedException;
import com.cybernetics.payment_ms.exception.NotEnoughProductException;
import com.cybernetics.payment_ms.feign.ProductFeignClient;
import com.cybernetics.payment_ms.mapper.PaymentHistoryMapper;
import com.cybernetics.payment_ms.repository.PaymentHistoryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {
    @Mock
    AzericardServiceImpl azericardService;
    @Mock
    ProductFeignClient productFeignClient;
    @Mock
    PaymentHistoryRepository paymentHistoryRepository;
    @Mock
    PaymentHistoryMapper paymentHistoryMapper;
    @Mock
    KafkaTemplate<String, KafkaRequestDto> kafkaTemplate;
    @Mock
    SecurityContext securityContext;
    @Mock
    Authentication authentication;

    @InjectMocks
    PaymentServiceImpl paymentService;

    PaymentRequestDto paymentRequestDto;
    PaymentHistoryEntity paymentHistoryEntity;
    GetStockPriceResponseDto stockPriceResponse;
    AzericardResponseDto azericardResponse;
    String userEmail;
    String username;

    @BeforeEach
    void setUp() {
        paymentRequestDto = MockData.paymentRequestDto();
        paymentHistoryEntity = MockData.paymentHistoryEntity();
        stockPriceResponse = MockData.stockPriceResponse(20);
        azericardResponse = MockData.azericardResponse("1");
        userEmail = MockData.USER_EMAIL;
        username = MockData.USERNAME;

        lenient().when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void payProductWhenPaymentIsSuccessful() {
        when(paymentHistoryMapper.mapDtoToEntity(paymentRequestDto)).thenReturn(paymentHistoryEntity);
        when(productFeignClient.getStockAndPrice(paymentRequestDto.productId())).thenReturn(stockPriceResponse);
        when(azericardService.payAzericard(paymentRequestDto)).thenReturn(azericardResponse);
        when(authentication.getName()).thenReturn(userEmail);
        when(authentication.getCredentials()).thenReturn(username);
        when(paymentHistoryRepository.save(any(PaymentHistoryEntity.class))).thenReturn(paymentHistoryEntity);

        PaymentResponseDto result = paymentService.payProduct(paymentRequestDto);

        assertNotNull(result);
        assertEquals(azericardResponse.orderId(),result.orderId());

        verify(paymentHistoryMapper).mapDtoToEntity(paymentRequestDto);
        verify(productFeignClient).getStockAndPrice(paymentRequestDto.productId());
        verify(azericardService).payAzericard(paymentRequestDto);
        verify(authentication).getName();
        verify(authentication).getCredentials();
        verify(paymentHistoryRepository, times(3)).save(paymentHistoryEntity);
        verify(kafkaTemplate).send(eq(PaymentServiceImpl.TOPIC), any(KafkaRequestDto.class));
        verify(productFeignClient).decreaseProductCount(any(DecreaseCountReqDto.class));
    }

    @Test
    void testWhenProductNotEnough() {
        GetStockPriceResponseDto lowStockResponse = MockData.stockPriceResponse(1);
        when(paymentHistoryMapper.mapDtoToEntity(paymentRequestDto)).thenReturn(paymentHistoryEntity);
        when(productFeignClient.getStockAndPrice(paymentRequestDto.productId())).thenReturn(lowStockResponse);

        assertThrows(NotEnoughProductException.class, () -> paymentService.payProduct(paymentRequestDto));

        verify(paymentHistoryMapper).mapDtoToEntity(paymentRequestDto);
        verify(productFeignClient).getStockAndPrice(paymentRequestDto.productId());
        verify(azericardService, never()).payAzericard(paymentRequestDto);
        verify(authentication, never()).getName();
        verify(authentication, never()).getCredentials();
        verify(paymentHistoryRepository, never()).save(paymentHistoryEntity);
        verify(kafkaTemplate, never()).send(eq(PaymentServiceImpl.TOPIC), any(KafkaRequestDto.class));
        verify(productFeignClient, never()).decreaseProductCount(any(DecreaseCountReqDto.class));
    }

    @Test
    void testWhenAzericardFailed() {
        AzericardResponseDto failedResponse = MockData.azericardResponse("0");
        when(paymentHistoryMapper.mapDtoToEntity(paymentRequestDto)).thenReturn(paymentHistoryEntity);
        when(productFeignClient.getStockAndPrice(paymentRequestDto.productId())).thenReturn(stockPriceResponse);
        when(azericardService.payAzericard(paymentRequestDto)).thenReturn(failedResponse);

        assertThrows(AzericardFailedException.class, () -> paymentService.payProduct(paymentRequestDto));

        verify(paymentHistoryMapper).mapDtoToEntity(paymentRequestDto);
        verify(productFeignClient).getStockAndPrice(paymentRequestDto.productId());
        verify(azericardService).payAzericard(paymentRequestDto);
        verify(authentication, never()).getName();
        verify(authentication, never()).getCredentials();
        verify(paymentHistoryRepository, times(3)).save(paymentHistoryEntity);
        verify(kafkaTemplate, never()).send(eq(PaymentServiceImpl.TOPIC), any(KafkaRequestDto.class));
        verify(productFeignClient, never()).decreaseProductCount(any(DecreaseCountReqDto.class));
    }
}