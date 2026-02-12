package com.cybernetics.product_ms.dto;

import com.cybernetics.product_ms.utils.SuccessStatus;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SuccessDto <T> {
    String status;
    T data;

    public SuccessDto(SuccessStatus successStatus, T productResponseDto) {
        this.status = successStatus.name();
        this.data = productResponseDto;
    }
}
