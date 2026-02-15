package com.cybernetics.user_management_ms.service.implement;

import com.cybernetics.user_management_ms.config.security.PasswordEncoderConfig;
import com.cybernetics.user_management_ms.dto.request.LoginRequestDto;
import com.cybernetics.user_management_ms.dto.request.RefreshTokenRequestDto;
import com.cybernetics.user_management_ms.dto.response.AuthResponseDto;
import com.cybernetics.user_management_ms.entity.RefreshTokenEntity;
import com.cybernetics.user_management_ms.entity.UserEntity;
import com.cybernetics.user_management_ms.exception.IncorrectPasswordException;
import com.cybernetics.user_management_ms.exception.InvalidRefreshTokenException;
import com.cybernetics.user_management_ms.exception.UserNotFoundException;
import com.cybernetics.user_management_ms.repository.RefreshTokenRepository;
import com.cybernetics.user_management_ms.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {
    @Mock
    UserRepository userRepository;
    @Mock
    RefreshTokenRepository refreshTokenRepository;
    @Mock
    PasswordEncoderConfig encoderConfig;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    JwtServiceImpl jwtService;

    @InjectMocks
    AuthServiceImpl authService;

    LoginRequestDto loginRequestDto;
    RefreshTokenRequestDto refreshTokenRequestDto;
    AuthResponseDto authResponseDto;
    RefreshTokenEntity refreshTokenEntity;
    RefreshTokenEntity expiredRefreshTokenEntity;
    RefreshTokenEntity revokedRefreshTokenEntity;
    UserEntity userEntity;

    @BeforeEach
    void setUp() {
        loginRequestDto = MockData.loginRequestDto();
        refreshTokenRequestDto = MockData.refreshTokenRequestDto();
        authResponseDto = MockData.authResponseDto();
        refreshTokenEntity = MockData.refreshTokenEntity();
        expiredRefreshTokenEntity = MockData.expiredRefreshTokenEntity();
        revokedRefreshTokenEntity = MockData.revokedRefreshTokenEntity();
        userEntity = MockData.userEntity();

        lenient().when(encoderConfig.bCryptEncoder()).thenReturn(passwordEncoder);
    }

    @Test
    void testWhenLoginSuccess() {
        when(userRepository.findByUsername(loginRequestDto.username())).thenReturn(Optional.of(userEntity));
        when(passwordEncoder.matches(loginRequestDto.password(), userEntity.getPassword())).thenReturn(true);
        when(jwtService.generateAccessToken(userEntity)).thenReturn("accessToken");
        when(jwtService.generateRefreshToken(userEntity)).thenReturn("refreshToken");

        AuthResponseDto authResponseDto = authService.login(loginRequestDto);

        assertNotNull(authResponseDto);
        assertEquals("accessToken", authResponseDto.accessToken());
        assertEquals("refreshToken", authResponseDto.refreshToken());

        verify(userRepository).findByUsername(loginRequestDto.username());
        verify(passwordEncoder).matches(loginRequestDto.password(), userEntity.getPassword());
        verify(jwtService).generateAccessToken(userEntity);
        verify(jwtService).generateRefreshToken(userEntity);
    }

    @Test
    void testWhenUserNotFoundForLogin() {
        when(userRepository.findByUsername(loginRequestDto.username())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> authService.login(loginRequestDto));

        verify(userRepository).findByUsername(loginRequestDto.username());
    }

    @Test
    void testWhenIncorrectPasswordForLogin() {
        when(userRepository.findByUsername(loginRequestDto.username())).thenReturn(Optional.of(userEntity));
        when(passwordEncoder.matches(loginRequestDto.password(), userEntity.getPassword())).thenReturn(false);

        assertThrows(IncorrectPasswordException.class, () -> authService.login(loginRequestDto));

        verify(userRepository).findByUsername(loginRequestDto.username());
        verify(passwordEncoder).matches(loginRequestDto.password(), userEntity.getPassword());
    }

    @Test
    void testWhenRefreshSuccess() {
        when(refreshTokenRepository.findByTokenHashAndRevokedAtIsNull(anyString())).thenReturn(Optional.of(refreshTokenEntity));
        when(jwtService.generateAccessToken(refreshTokenEntity.getUser())).thenReturn("newAccessToken");

        AuthResponseDto authResponseDto = authService.refresh(refreshTokenRequestDto);

        assertEquals("newAccessToken", authResponseDto.accessToken());
        assertEquals(refreshTokenRequestDto.refreshToken(), authResponseDto.refreshToken());

        verify(refreshTokenRepository).findByTokenHashAndRevokedAtIsNull(anyString());
        verify(jwtService).generateAccessToken(refreshTokenEntity.getUser());
    }

    @Test
    void testWhenRefreshTokenIsInvalid() {
        when(refreshTokenRepository.findByTokenHashAndRevokedAtIsNull(anyString())).thenReturn(Optional.empty());

        assertThrows(InvalidRefreshTokenException.class, () -> authService.refresh(refreshTokenRequestDto));

        verify(refreshTokenRepository).findByTokenHashAndRevokedAtIsNull(anyString());
    }

    @Test
    void testWhenRefreshTokenIsExpired() {
        when(refreshTokenRepository.findByTokenHashAndRevokedAtIsNull(anyString())).thenReturn(Optional.of(expiredRefreshTokenEntity));

        assertThrows(InvalidRefreshTokenException.class, () -> authService.refresh(refreshTokenRequestDto));

        verify(refreshTokenRepository).findByTokenHashAndRevokedAtIsNull(anyString());
    }

    @Test
    void testWhenRefreshTokenIsRevoked() {
        when(refreshTokenRepository.findByTokenHashAndRevokedAtIsNull(anyString())).thenReturn(Optional.of(revokedRefreshTokenEntity));

        assertThrows(InvalidRefreshTokenException.class, () -> authService.refresh(refreshTokenRequestDto));

        verify(refreshTokenRepository).findByTokenHashAndRevokedAtIsNull(anyString());
    }
}