package com.cybernetics.user_management_ms.repository;

import com.cybernetics.user_management_ms.entity.RefreshTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, String> {

    Optional<RefreshTokenEntity> findByTokenHashAndRevokedAtIsNull(String tokenHash);

}
