package com.cybernetics.user_management_ms.service.implement;

import com.cybernetics.user_management_ms.dto.request.UserRequestDto;
import com.cybernetics.user_management_ms.dto.response.UserResponseDto;
import com.cybernetics.user_management_ms.entity.UserEntity;
import com.cybernetics.user_management_ms.utils.UserRole;

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
}
