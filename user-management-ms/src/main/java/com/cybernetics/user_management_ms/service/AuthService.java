package com.cybernetics.user_management_ms.service;

import com.cybernetics.user_management_ms.dto.request.LoginRequestDto;
import com.cybernetics.user_management_ms.dto.request.RefreshTokenRequestDto;
import com.cybernetics.user_management_ms.dto.response.AuthResponseDto;

public interface AuthService {

    AuthResponseDto login(LoginRequestDto loginRequestDto);

    AuthResponseDto refresh(RefreshTokenRequestDto refreshTokenRequestDto);

}
