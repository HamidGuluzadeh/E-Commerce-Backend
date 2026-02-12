package com.cybernetics.user_management_ms.service.implement;

import com.cybernetics.user_management_ms.config.jwt.JwtProperties;
import com.cybernetics.user_management_ms.entity.RefreshTokenEntity;
import com.cybernetics.user_management_ms.entity.UserEntity;
import com.cybernetics.user_management_ms.repository.RefreshTokenRepository;
import com.cybernetics.user_management_ms.service.JwtService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.apache.commons.codec.digest.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JwtServiceImpl implements JwtService {
    final JwtProperties jwtProperties;
    final RefreshTokenRepository refreshTokenRepository;
    Key accessTokenKey;

    @PostConstruct
    void init() {
        String secret = jwtProperties.accessToken().secret();

        if (Objects.isNull(secret) || secret.length() < 32) {
            throw new IllegalStateException("JWT access token secret must be at least 256 bits!");
        }

        this.accessTokenKey = Keys.hmacShaKeyFor(
                jwtProperties.accessToken().secret().getBytes(StandardCharsets.UTF_8)
        );
    }

    @Override
    public String generateAccessToken(UserEntity userEntity) {
        Instant now = Instant.now();
        Instant expiry = now.plus(jwtProperties.accessToken()
                .expirationMinutes(),
                ChronoUnit.MINUTES);

        return Jwts.builder()
                .setSubject(userEntity.getUserId().toString())
                .claim("username", userEntity.getUsername())
                .claim("role", userEntity.getUserRole().name())
                .claim("email", userEntity.getEmail())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expiry))
                .signWith(accessTokenKey, SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public String generateRefreshToken(UserEntity userEntity) {
        Instant now = Instant.now();
        Instant expiresAt = now.plus(
                jwtProperties.refreshToken().expirationDays(),
                ChronoUnit.DAYS
        );

        String tokenId = UUID.randomUUID().toString();
        String tokenValue = generateSecureRandomToken(64);

        RefreshTokenEntity refreshToken = RefreshTokenEntity.builder()
                .user(userEntity)
                .tokenId(tokenId)
                .tokenHash(hashToken(tokenValue))
                .expiresAt(expiresAt)
                .revokedAt(null)
                .createdAt(now)
                .build();

        refreshTokenRepository.save(refreshToken);

        return tokenValue;
    }

    private String generateSecureRandomToken(int length) {
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder stringBuilder = new StringBuilder(length);
        String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

        for (int i = 0; i < length; i++) {
            int idx = secureRandom.nextInt(chars.length());
            stringBuilder.append(chars.charAt(idx));
        }

        return stringBuilder.toString();
    }

    private String hashToken(String tokenValue) {
        return DigestUtils.sha256Hex(tokenValue);
    }

}
