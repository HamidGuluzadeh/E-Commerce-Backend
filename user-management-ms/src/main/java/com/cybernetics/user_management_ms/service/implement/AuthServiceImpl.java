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
import com.cybernetics.user_management_ms.service.AuthService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthServiceImpl implements AuthService {
    UserRepository userRepository;
    RefreshTokenRepository refreshTokenRepository;
    PasswordEncoderConfig passwordEncoder;
    JwtServiceImpl jwtService;

    @Override
    public Optional<AuthResponseDto> login(LoginRequestDto loginRequestDto) {
        UserEntity userEntity = userRepository.findByUsername(loginRequestDto.username())
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + loginRequestDto.username()));

        boolean isValid = passwordEncoder.bCryptEncoder().matches(loginRequestDto.password(), userEntity.getPassword());

        if (!isValid) {
            throw new IncorrectPasswordException("Incorrect password!");
        }

        String accessToken = jwtService.generateAccessToken(userEntity);
        String refreshToken = jwtService.generateRefreshToken(userEntity);

        return Optional.of(new AuthResponseDto(accessToken, refreshToken));
    }

    @Override
    public Optional<AuthResponseDto> refresh(RefreshTokenRequestDto refreshTokenRequestDto) {
        String refreshToken = refreshTokenRequestDto.refreshToken();

        String tokenHash = hashToken(refreshToken);

        RefreshTokenEntity refreshTokenEntity = refreshTokenRepository.findByTokenHashAndRevokedAtIsNull(tokenHash)
                .orElseThrow(() -> new InvalidRefreshTokenException("Invalid refresh token!"));

        if (refreshTokenEntity.getExpiresAt().isBefore(Instant.now())) {
            throw new InvalidRefreshTokenException("Refresh token expired!");
        } else if (!Objects.isNull(refreshTokenEntity.getRevokedAt())) {
            throw new InvalidRefreshTokenException("Refresh token revoked!");
        }

        UserEntity userEntity = refreshTokenEntity.getUser();

        String newAccessToken = jwtService.generateAccessToken(userEntity);

        return Optional.of(new AuthResponseDto(newAccessToken, refreshToken));
    }

    private String hashToken(String tokenValue) {
        return DigestUtils.sha256Hex(tokenValue);
    }
}
