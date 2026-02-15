package com.cybernetics.user_management_ms.service.implement;

import com.cybernetics.user_management_ms.dto.request.LoginRequestDto;
import com.cybernetics.user_management_ms.dto.request.RefreshTokenRequestDto;
import com.cybernetics.user_management_ms.dto.request.UserNameRequestDto;
import com.cybernetics.user_management_ms.dto.request.UserRequestDto;
import com.cybernetics.user_management_ms.dto.response.AuthResponseDto;
import com.cybernetics.user_management_ms.dto.response.UserNameResponseDto;
import com.cybernetics.user_management_ms.dto.response.UserResponseDto;
import com.cybernetics.user_management_ms.entity.RefreshTokenEntity;
import com.cybernetics.user_management_ms.entity.UserEntity;
import com.cybernetics.user_management_ms.utils.UserRole;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

public class MockData {
    public static UserRequestDto userRequestDto() {
        return UserRequestDto.builder()
                .username("username")
                .firstName("firstName")
                .lastName("lastName")
                .password("password")
                .userRole(UserRole.USER)
                .build();
    }

    public static UserEntity userEntity() {
        return UserEntity.builder()
                .username("username")
                .firstName("firstName")
                .lastName("lastName")
                .password("password")
                .userRole(UserRole.USER)
                .build();
    }

    public static UserResponseDto userResponseDto() {
        return UserResponseDto.builder()
                .username("username")
                .firstName("firstName")
                .lastName("lastName")
                .userRole(UserRole.USER)
                .build();
    }

    public static UserNameRequestDto userNameRequestDto() {
        return UserNameRequestDto.builder()
                .firstName("firstName")
                .lastName("lastName")
                .build();
    }

    public static UserNameResponseDto userNameResponseDto() {
        return UserNameResponseDto.builder()
                .username("username")
                .firstName("firstName")
                .lastName("lastName")
                .build();
    }

    public static List<UserEntity> getUserEntityList() {
        UserEntity userEntity1 = UserEntity.builder()
                .username("username1")
                .firstName("firstName1")
                .lastName("lastName1")
                .password("password1")
                .userRole(UserRole.USER)
                .build();

        UserEntity userEntity2 = UserEntity.builder()
                .username("username2")
                .firstName("firstName2")
                .lastName("lastName2")
                .password("password2")
                .userRole(UserRole.SELLER)
                .build();

        return Arrays.asList(userEntity1, userEntity2);
    }

    public static LoginRequestDto loginRequestDto() {
        return LoginRequestDto.builder()
                .username("username")
                .password("password")
                .build();
    }

    public static RefreshTokenRequestDto refreshTokenRequestDto() {
        return RefreshTokenRequestDto.builder()
                .refreshToken("refreshToken")
                .build();
    }

    public static AuthResponseDto authResponseDto() {
        return AuthResponseDto.builder()
                .accessToken("accessToken")
                .refreshToken("refreshToken")
                .build();
    }

    public static RefreshTokenEntity refreshTokenEntity() {
        return RefreshTokenEntity.builder()
                .id("id")
                .tokenId("token-uuid")
                .tokenHash("hashed-token-value")
                .user(userEntity())
                .createdAt(Instant.now())
                .expiresAt(Instant.now().plus(30, ChronoUnit.DAYS))
                .revokedAt(null)
                .build();
    }

    public static RefreshTokenEntity expiredRefreshTokenEntity() {
        return RefreshTokenEntity.builder()
                .id("id")
                .tokenId("token-uuid")
                .tokenHash("hashed-token-value")
                .user(userEntity())
                .createdAt(Instant.now().minus(30, ChronoUnit.DAYS))
                .expiresAt(Instant.now().minusSeconds(1))
                .revokedAt(null)
                .build();
    }

    public static RefreshTokenEntity revokedRefreshTokenEntity() {
        return RefreshTokenEntity.builder()
                .id("id")
                .tokenId("token-uuid")
                .tokenHash("hashed-token-value")
                .user(userEntity())
                .createdAt(Instant.now())
                .expiresAt(Instant.now().plus(1, ChronoUnit.DAYS))
                .revokedAt(Instant.now())
                .build();
    }
}
