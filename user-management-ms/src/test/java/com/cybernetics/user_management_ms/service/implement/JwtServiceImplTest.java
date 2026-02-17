package com.cybernetics.user_management_ms.service.implement;

import com.cybernetics.user_management_ms.config.jwt.JwtProperties;
import com.cybernetics.user_management_ms.entity.RefreshTokenEntity;
import com.cybernetics.user_management_ms.entity.UserEntity;
import com.cybernetics.user_management_ms.repository.RefreshTokenRepository;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.charset.StandardCharsets;
import java.security.Key;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtServiceImplTest {
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    JwtProperties jwtProperties;
    @Mock
    RefreshTokenRepository refreshTokenRepository;
    @Mock
    Key accessTokenKey;

    @InjectMocks
    JwtServiceImpl jwtService;

    UserEntity userEntity;
    String secretKey = "CHANGE_THIS_TO_A_VERY_LONG_RANDOM_SECRET_KEY_256_BITS_ACCESS";

    @BeforeEach
    void setUp() {
        userEntity = MockData.userEntity2();

        MockData.configureJwtProperties(jwtProperties, secretKey);
        jwtService.init();
    }

    @Test
    void testWhenAccessTokenGeneratedSuccess() {
        String token = jwtService.generateAccessToken(userEntity);

        assertNotNull(token);
        assertFalse(token.isBlank());
        assertDoesNotThrow(() -> Jwts.parser()
                .setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8))
                .build()
                .parseClaimsJws(token));
    }

    @Test
    void testWhenSecretKeyIsWeak() {
        when(jwtProperties.accessToken().secret()).thenReturn("123456789");

        assertThrows(IllegalStateException.class, () -> jwtService.init());
    }

    @Test
    void testWhenRefreshTokenGeneratedSuccess() {
        String token = jwtService.generateRefreshToken(userEntity);

        assertNotNull(token);
        assertEquals(64, token.length());

        verify(refreshTokenRepository).save(any(RefreshTokenEntity.class));
    }
}