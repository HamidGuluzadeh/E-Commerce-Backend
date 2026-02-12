package com.cybernetics.user_management_ms.service;

import com.cybernetics.user_management_ms.dto.request.LoginRequestDto;
import com.cybernetics.user_management_ms.dto.request.RefreshTokenRequestDto;
import com.cybernetics.user_management_ms.dto.response.AuthResponseDto;

import java.util.Optional;

public interface AuthService {

    Optional<AuthResponseDto> login(LoginRequestDto loginRequestDto);

    Optional<AuthResponseDto> refresh(RefreshTokenRequestDto refreshTokenRequestDto);

}
