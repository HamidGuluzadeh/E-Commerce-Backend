package com.cybernetics.user_management_ms.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record RefreshTokenRequestDto(@NotBlank(message = "Refresh token cannot be empty!")
                                     String refreshToken) {
}
