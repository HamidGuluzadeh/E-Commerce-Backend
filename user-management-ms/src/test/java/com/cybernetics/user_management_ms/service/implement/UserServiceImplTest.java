package com.cybernetics.user_management_ms.service.implement;

import com.cybernetics.user_management_ms.config.security.PasswordEncoderConfig;
import com.cybernetics.user_management_ms.dto.request.UserRequestDto;
import com.cybernetics.user_management_ms.dto.response.UserResponseDto;
import com.cybernetics.user_management_ms.entity.UserEntity;
import com.cybernetics.user_management_ms.mapper.UserMapper;
import com.cybernetics.user_management_ms.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    UserRepository userRepository;
    @Mock
    UserMapper userMapper;
    @Mock
    PasswordEncoderConfig encoder;

    @InjectMocks
    UserServiceImpl userService;

    UserRequestDto  userRequestDto;
    UserEntity userEntity;
    UserResponseDto userResponseDto;

    String username;

    @BeforeEach
    void setUp() {
        userRequestDto = MockData.userRequestDto();
        userEntity = MockData.userEntity();
        userResponseDto = MockData.userResponseDto();

        username = userEntity.getUsername();
    }

    @Test
    void testWhenUserIsFoundByUsername() {
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(userEntity));
        when(userMapper.mapEntityToResponse(userEntity)).thenReturn(userResponseDto);

        userService.findByUsername(username);

        verify(userRepository).findByUsername(username);
        verify(userMapper).mapEntityToResponse(userEntity);
    }

    @Test
    void testWhenUserIsCreated() {
        when(userRepository.existsByUsername(userRequestDto.username())).thenReturn(false);
        when(userMapper.mapRequestToEntity(userRequestDto)).thenReturn(userEntity);
        when(encoder.bCryptEncoder().encode(userRequestDto.password())).thenReturn("User password has been hashed!");
        when(userRepository.save(userEntity)).thenReturn(userEntity);
        when(userMapper.mapEntityToResponse(userEntity)).thenReturn(userResponseDto);

        userService.createUser(userRequestDto);

        verify(userRepository).existsByUsername(userRequestDto.username());
        verify(userMapper).mapRequestToEntity(userRequestDto);
        verify(encoder).bCryptEncoder().encode(userRequestDto.password());
        verify(userRepository).save(userEntity);
        verify(userMapper).mapEntityToResponse(userEntity);
    }

}