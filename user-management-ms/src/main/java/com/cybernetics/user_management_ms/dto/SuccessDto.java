package com.cybernetics.user_management_ms.dto;

import com.cybernetics.user_management_ms.utils.SuccessStatus;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SuccessDto <T> {
    String status;
    T data;

    public SuccessDto(SuccessStatus successStatus, T userResponseDto) {
        this.status = successStatus.name();
        this.data = userResponseDto;
    }
}
