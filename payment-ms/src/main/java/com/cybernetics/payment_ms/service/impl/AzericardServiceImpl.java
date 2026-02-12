package com.cybernetics.payment_ms.service.impl;

import com.cybernetics.payment_ms.dto.request.PaymentRequestDto;
import com.cybernetics.payment_ms.dto.response.AzericardResponseDto;
import com.cybernetics.payment_ms.service.AzericardService;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AzericardServiceImpl implements AzericardService {

    private static final Random RANDOM = new Random();

    @Override
    public AzericardResponseDto payAzericard(PaymentRequestDto paymentRequestDto) {

        int orderId = 100000 + RANDOM.nextInt(900000);

        return AzericardResponseDto.builder()
                .orderId(String.valueOf(orderId))
                .status("1")
                .build();
    }
}
