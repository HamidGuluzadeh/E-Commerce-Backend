package com.cybernetics.payment_ms.service;

import com.cybernetics.payment_ms.dto.request.PaymentRequestDto;
import com.cybernetics.payment_ms.dto.response.AzericardResponseDto;

public interface AzericardService {

    AzericardResponseDto payAzericard(PaymentRequestDto paymentRequestDto);
}
