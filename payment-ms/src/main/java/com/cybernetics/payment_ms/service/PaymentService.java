package com.cybernetics.payment_ms.service;

import com.cybernetics.payment_ms.dto.request.PaymentRequestDto;
import com.cybernetics.payment_ms.dto.response.PaymentResponseDto;

public interface PaymentService {

    PaymentResponseDto payProduct(PaymentRequestDto paymentRequestDto);
}
