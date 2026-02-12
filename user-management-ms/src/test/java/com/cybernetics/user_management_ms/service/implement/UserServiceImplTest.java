package com.cybernetics.user_management_ms.service.implement;

import com.cybernetics.user_management_ms.config.security.PasswordEncoderConfig;
import com.cybernetics.user_management_ms.dto.request.UserNameRequestDto;
import com.cybernetics.user_management_ms.dto.request.UserRequestDto;
import com.cybernetics.user_management_ms.dto.response.UserNameResponseDto;
import com.cybernetics.user_management_ms.dto.response.UserResponseDto;
import com.cybernetics.user_management_ms.entity.UserEntity;
import com.cybernetics.user_management_ms.exception.UserAlreadyExistsException;
import com.cybernetics.user_management_ms.exception.UserNotFoundException;
import com.cybernetics.user_management_ms.mapper.UserMapper;
import com.cybernetics.user_management_ms.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    UserRepository userRepository;
    @Mock
    UserMapper userMapper;
    @Mock
    PasswordEncoderConfig encoderConfig;
    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    UserServiceImpl userService;

    UserRequestDto  userRequestDto;
    UserEntity userEntity;
    UserResponseDto userResponseDto;
    UserNameRequestDto userNameRequestDto;
    UserNameResponseDto userNameResponseDto;
    String username;

    @BeforeEach
    void setUp() {
        userRequestDto = MockData.userRequestDto();
        userEntity = MockData.userEntity();
        userResponseDto = MockData.userResponseDto();
        userNameRequestDto = MockData.userNameRequestDto();
        userNameResponseDto = MockData.userNameResponseDto();
        username = userEntity.getUsername();

        lenient().when(encoderConfig.bCryptEncoder()).thenReturn(passwordEncoder);
    }

    @Test
    void testWhenGetAllUsers() {
        List<UserEntity> userEntityList = MockData.getUserEntityList();

        when(userRepository.findAll()).thenReturn(userEntityList);
        when(userMapper.mapEntityToResponse(any(UserEntity.class))).thenReturn(userResponseDto);

        List<UserResponseDto> userResponseDtoList= userService.getAllUsers();

        assertNotNull(userResponseDtoList);
        assertEquals(2, userResponseDtoList.size());

        verify(userRepository).findAll();
        verify(userMapper, times(userEntityList.size())).mapEntityToResponse(any(UserEntity.class));
    }

    @Test
    void testWhenUserIsFoundByUsernameSuccess() {
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(userEntity));
        when(userMapper.mapEntityToResponse(userEntity)).thenReturn(userResponseDto);

        UserResponseDto userResponseDto = userService.findByUsername(username);

        assertNotNull(userResponseDto);

        verify(userRepository).findByUsername(username);
        verify(userMapper).mapEntityToResponse(userEntity);
    }

    @Test
    void testWhenUserIsNotFoundForGet() {
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.findByUsername(username));

        verify(userRepository).findByUsername(username);
    }

    @Test
    void testWhenUserIsFoundByFirstNameAndLastNameBothProvided() {
        UserEntity user1 = UserEntity.builder().firstName("Hamid").lastName("Guluzadeh").build();
        UserEntity user2 = UserEntity.builder().firstName("Ali").lastName("Ahmadov").build();
        List<UserEntity> userEntityList = List.of(user1, user2);

        when(userRepository.findAll()).thenReturn(userEntityList);
        when(userMapper.mapEntityToResponse(user1)).thenReturn(userResponseDto);

        List<UserResponseDto> userResponseDtoList = userService.findByFirstNameAndLastNameIgnoreCase("Hamid", "Guluzadeh");

        assertNotNull(userResponseDtoList);
        assertEquals(1, userResponseDtoList.size());

        verify(userRepository).findAll();
    }

    @Test
    void testWhenUserIsFoundByFirstNameAndLastNameOnlyFirstName() {
        UserEntity user1 = UserEntity.builder().firstName("Hamid").lastName("Guluzadeh").build();
        UserEntity user2 = UserEntity.builder().firstName("Hamid").lastName("Aliyev").build();
        List<UserEntity> userEntityList = List.of(user1, user2);

        when(userRepository.findAll()).thenReturn(userEntityList);
        when(userMapper.mapEntityToResponse(user1)).thenReturn(userResponseDto);

        List<UserResponseDto> userResponseDtoList = userService.findByFirstNameAndLastNameIgnoreCase("Hamid", null);

        assertNotNull(userResponseDtoList);
        assertEquals(2, userResponseDtoList.size());

        verify(userRepository).findAll();
    }

    @Test
    void testWhenUserIsFoundByFirstNameAndLastNameAllNull() {
        when(userRepository.findAll()).thenReturn(MockData.getUserEntityList());
        when(userMapper.mapEntityToResponse(any(UserEntity.class))).thenReturn(userResponseDto);

        List<UserResponseDto> userResponseDtoList = userService.findByFirstNameAndLastNameIgnoreCase(null, null);

        assertNotNull(userResponseDtoList);
        assertEquals(2, userResponseDtoList.size());

        verify(userRepository).findAll();
    }

    @Test
    void testWhenUserIsCreatedSuccess() {
        when(userRepository.existsByUsername(userRequestDto.username())).thenReturn(false);
        when(userMapper.mapRequestToEntity(userRequestDto)).thenReturn(userEntity);
        when(passwordEncoder.encode(userRequestDto.password())).thenReturn("Password has been hashed!");
        when(userRepository.save(userEntity)).thenReturn(userEntity);
        when(userMapper.mapEntityToResponse(userEntity)).thenReturn(userResponseDto);

        UserResponseDto userResponseDto = userService.createUser(userRequestDto);

        assertNotNull(userResponseDto);

        verify(userRepository).existsByUsername(userRequestDto.username());
        verify(userMapper).mapRequestToEntity(userRequestDto);
        verify(passwordEncoder).encode(userRequestDto.password());
        verify(userRepository).save(userEntity);
        verify(userMapper).mapEntityToResponse(userEntity);
    }

    @Test
    void testWhenUserAlreadyExists() {
        when(userRepository.existsByUsername(userRequestDto.username())).thenReturn(true);

        assertThrows(UserAlreadyExistsException.class, () -> userService.createUser(userRequestDto));

        verify(userRepository).existsByUsername(userRequestDto.username());
    }

    @Test
    void testWhenUserIsUpdatedSuccess() {
        when(userRepository.findByUsername(userRequestDto.username())).thenReturn(Optional.ofNullable(userEntity));
        when(userMapper.mapRequestToEntity(userRequestDto)).thenReturn(userEntity);
        when(passwordEncoder.encode(userRequestDto.password())).thenReturn("Password has been hashed!");
        when(userMapper.mapEntityToResponse(userEntity)).thenReturn(userResponseDto);

        UserResponseDto userResponseDto = userService.updateUser(userRequestDto, username);

        assertNotNull(userResponseDto);

        verify(userRepository).findByUsername(userRequestDto.username());
        verify(userMapper).mapRequestToEntity(userRequestDto);
        verify(passwordEncoder).encode(userRequestDto.password());
        verify(userMapper).mapEntityToResponse(userEntity);
    }

    @Test
    void testWhenUserIsNotFoundForUpdate() {
        when(userRepository.findByUsername(userRequestDto.username())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.updateUser(userRequestDto, username));

        verify(userRepository).findByUsername(userRequestDto.username());
    }

    @Test
    void testWhenUserIsUpdatedByNameSuccess() {
        when(userRepository.findByUsername(username)).thenReturn(Optional.ofNullable(userEntity));
        when(userMapper.mapEntityToNameResponse(userEntity)).thenReturn(userNameResponseDto);

        UserNameResponseDto userNameResponseDto = userService.updateName(userNameRequestDto, username);

        assertNotNull(userNameResponseDto);
        assertEquals(userNameRequestDto.firstName(), userEntity.getFirstName());
        assertEquals(userNameRequestDto.lastName(), userEntity.getLastName());

        verify(userRepository).findByUsername(username);
        verify(userMapper).mapEntityToNameResponse(userEntity);
    }

    @Test
    void testWhenUserIsNotFoundForUpdateName() {
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.updateName(userNameRequestDto, username));

        verify(userRepository).findByUsername(username);
    }

    @Test
    void testWhenUserIsDeletedSuccess() {
        when(userRepository.existsByUsername(username)).thenReturn(true);

        userService.deleteByUsername(username);

        verify(userRepository).existsByUsername(username);
    }

    @Test
    void testWhenUserIsNotFoundForDelete() {
        when(userRepository.existsByUsername(username)).thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> userService.deleteByUsername(username));

        verify(userRepository).existsByUsername(username);
    }
}