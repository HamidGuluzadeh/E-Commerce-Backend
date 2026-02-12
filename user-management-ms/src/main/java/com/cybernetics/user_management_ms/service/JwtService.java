package com.cybernetics.user_management_ms.service;

import com.cybernetics.user_management_ms.entity.RefreshTokenEntity;
import com.cybernetics.user_management_ms.entity.UserEntity;

public interface JwtService {

    String generateAccessToken(UserEntity userEntity);

    String generateRefreshToken(UserEntity userEntity);

}
