package com.cybernetics.user_management_ms.dto.response;

import lombok.Builder;

@Builder
public record AuthResponseDto(String accessToken,
                              String refreshToken) {
}
