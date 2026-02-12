package com.cybernetics.payment_ms.service.impl;

import com.cybernetics.payment_ms.dto.request.KafkaRequestDto;
import com.cybernetics.payment_ms.dto.request.PaymentRequestDto;
import com.cybernetics.payment_ms.entity.PaymentHistoryEntity;
import com.cybernetics.payment_ms.feign.ProductFeignClient;
import com.cybernetics.payment_ms.mapper.PaymentHistoryMapper;
import com.cybernetics.payment_ms.repository.PaymentHistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.Mockito.when;

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

    @InjectMocks
    PaymentServiceImpl paymentService;

    PaymentRequestDto paymentRequestDto;
    PaymentHistoryEntity paymentHistoryEntity;

    @BeforeEach
    void setUp() {
        paymentRequestDto = MockData.paymentRequestDto();
        paymentHistoryEntity = MockData.paymentHistoryEntity();
    }

    @Test
    void payProductWhenPaymentIsSuccessful() {
        when(paymentHistoryMapper.mapDtoToEntity(paymentRequestDto)).thenReturn(paymentHistoryEntity);

    }
}