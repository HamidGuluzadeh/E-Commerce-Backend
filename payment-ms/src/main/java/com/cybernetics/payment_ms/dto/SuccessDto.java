package com.cybernetics.payment_ms.dto;

import com.cybernetics.payment_ms.utils.Status;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SuccessDto<T> {
    String status;
    T data;

    public SuccessDto(Status successStatus, T paymentResponseDto) {
        this.status = successStatus.name();
        this.data = paymentResponseDto;
    }

}
