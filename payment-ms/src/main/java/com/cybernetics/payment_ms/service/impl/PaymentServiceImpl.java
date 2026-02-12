package com.cybernetics.payment_ms.service.impl;

import com.cybernetics.payment_ms.dto.request.DecreaseCountReqDto;
import com.cybernetics.payment_ms.dto.request.KafkaRequestDto;
import com.cybernetics.payment_ms.dto.request.PaymentRequestDto;
import com.cybernetics.payment_ms.dto.response.AzericardResponseDto;
import com.cybernetics.payment_ms.dto.response.PaymentResponseDto;
import com.cybernetics.payment_ms.entity.PaymentHistoryEntity;
import com.cybernetics.payment_ms.exception.AzericardFailedException;
import com.cybernetics.payment_ms.exception.NotEnoughProductException;
import com.cybernetics.payment_ms.feign.ProductFeignClient;
import com.cybernetics.payment_ms.mapper.PaymentHistoryMapper;
import com.cybernetics.payment_ms.repository.PaymentHistoryRepository;
import com.cybernetics.payment_ms.service.PaymentService;
import com.cybernetics.payment_ms.utils.PaymentStatus;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.cybernetics.payment_ms.utils.PaymentStatus.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentServiceImpl implements PaymentService {
    static String TOPIC = "kafka-payment-topic";

    AzericardServiceImpl azericardService;
    ProductFeignClient productFeignClient;
    PaymentHistoryRepository paymentHistoryRepository;
    PaymentHistoryMapper paymentHistoryMapper;
    KafkaTemplate<String, KafkaRequestDto> kafkaTemplate;

    @Override
    @Transactional
    public PaymentResponseDto payProduct(PaymentRequestDto paymentRequestDto) {
        PaymentHistoryEntity paymentHistoryEntity =
                paymentHistoryMapper.mapDtoToEntity(paymentRequestDto);

        Integer productCount = productFeignClient.getProductCount(paymentRequestDto.productId());

        BigDecimal productPrice = productFeignClient.getProductPrice(paymentRequestDto.productId());

        System.out.println("DEBUG: Product ID: " + paymentRequestDto.productId());
        System.out.println("DEBUG: Product count: " + productCount);

        if (paymentRequestDto.quantity() > productCount) {
            throw new NotEnoughProductException("Not enough products!");
        }

        BigDecimal totalAmount = productPrice.multiply(new BigDecimal(paymentRequestDto.quantity()));


        paymentHistoryEntity.setStatus(NEW.name());
        paymentHistoryEntity.setTotalAmount(totalAmount);

        paymentHistoryRepository.save(paymentHistoryEntity);

        paymentHistoryEntity.setStatus(PENDING.name());
        paymentHistoryRepository.save(paymentHistoryEntity);

        AzericardResponseDto azericardResponseDto =
                azericardService.payAzericard(paymentRequestDto);

        if (!"1".equals(azericardResponseDto.status())) {
            paymentHistoryEntity.setAzericardStatus(azericardResponseDto.status());
            paymentHistoryEntity.setStatus(PAYMENT_FAILED.name());
            paymentHistoryRepository.save(paymentHistoryEntity);

            throw new AzericardFailedException("Payment failed!");
        }

        paymentHistoryEntity.setOrderId(azericardResponseDto.orderId());
        paymentHistoryEntity.setAzericardStatus(azericardResponseDto.status());
        paymentHistoryEntity.setStatus(PaymentStatus.SUCCESS.name());

        paymentHistoryRepository.save(paymentHistoryEntity);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        String username = auth.getCredentials().toString();

        kafkaTemplate.send(TOPIC, KafkaRequestDto.builder()
                .email(email)
                .orderId(azericardResponseDto.orderId())
                .username(username)
                .build());

        productFeignClient.decreaseProductCount(DecreaseCountReqDto.builder()
                .count(paymentHistoryEntity.getQuantity())
                .productId(paymentHistoryEntity.getProductId())
                .build());

        return PaymentResponseDto.builder()
                .orderId(paymentHistoryEntity.getOrderId())
                .status(paymentHistoryEntity.getStatus())
                .totalAmount(paymentHistoryEntity.getTotalAmount())
                .build();
    }
}
