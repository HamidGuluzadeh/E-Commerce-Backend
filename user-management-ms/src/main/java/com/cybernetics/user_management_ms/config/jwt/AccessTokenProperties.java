package com.cybernetics.user_management_ms.config.jwt;

public record AccessTokenProperties(String secret,
                                    int expirationMinutes) {

}
