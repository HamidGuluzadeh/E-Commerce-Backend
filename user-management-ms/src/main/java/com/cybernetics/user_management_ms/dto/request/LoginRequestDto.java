package com.cybernetics.user_management_ms.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record LoginRequestDto(@NotBlank String username,
                              @NotBlank @Size(min = 8, message = "Password must contain at least 8 characters!")
                              String password) {
}
