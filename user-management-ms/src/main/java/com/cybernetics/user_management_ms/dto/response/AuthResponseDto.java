package com.cybernetics.user_management_ms.dto.response;

public record AuthResponseDto(String accessToken,
                              String refreshToken) {
}
