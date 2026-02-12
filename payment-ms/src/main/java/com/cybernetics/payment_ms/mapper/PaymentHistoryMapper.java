package com.cybernetics.payment_ms.mapper;

import com.cybernetics.payment_ms.dto.request.PaymentRequestDto;
import com.cybernetics.payment_ms.entity.PaymentHistoryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentHistoryMapper {

    PaymentHistoryEntity mapDtoToEntity(PaymentRequestDto paymentRequestDto);
}
