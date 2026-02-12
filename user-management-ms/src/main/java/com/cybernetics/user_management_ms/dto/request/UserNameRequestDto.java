package com.cybernetics.user_management_ms.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record UserNameRequestDto(@NotBlank String firstName,
                                 String lastName) {
}
