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
import com.cybernetics.user_management_ms.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoderConfig encoder;

    @Override
    public List<UserResponseDto> getAllUsers() {
        List<UserEntity> userEntities = userRepository.findAll();

        List<UserResponseDto> userResponseDtoList = new ArrayList<>();

        for (UserEntity userEntity : userEntities) {
            userResponseDtoList.add(userMapper.mapEntityToResponse(userEntity));
        }

        return userResponseDtoList;
    }

    @Override
    public UserResponseDto findByUsername(String username) {
        UserEntity userEntity = userRepository.findByUsername(username)
                        .orElseThrow(() -> new UserNotFoundException("User not found! : " + username));

        return userMapper.mapEntityToResponse(userEntity);
    }

    @Override
    public List<UserResponseDto> findByFirstNameAndLastNameIgnoreCase(String firstName, String lastName) {
        List<UserEntity> userEntities = userRepository.findAll();

        List<UserResponseDto> userResponseDtoList = new ArrayList<>();

        for (UserEntity userEntity : userEntities) {
            if (Objects.isNull(firstName) && Objects.isNull(lastName)) {
                userResponseDtoList.add(userMapper.mapEntityToResponse(userEntity));
            } else if (!Objects.isNull(firstName) && Objects.isNull(lastName)) {
                if (userEntity.getFirstName().equalsIgnoreCase(firstName)) {
                    userResponseDtoList.add(userMapper.mapEntityToResponse(userEntity));
                }
            } else if (Objects.isNull(firstName) && !(Objects.isNull(lastName))) {
                if (userEntity.getLastName().equalsIgnoreCase(lastName)) {
                    userResponseDtoList.add(userMapper.mapEntityToResponse(userEntity));
                }
            } else {
                if (userEntity.getFirstName().equalsIgnoreCase(firstName) &&
                        userEntity.getLastName().equalsIgnoreCase(lastName)) {
                    userResponseDtoList.add(userMapper.mapEntityToResponse(userEntity));
                }
            }
        }

        return userResponseDtoList;
    }

    @Override
    @Transactional
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        if (userRepository.existsByUsername(userRequestDto.username())) {
            throw new UserAlreadyExistsException("User already exists with username:" + userRequestDto.username());
        }

        UserEntity userEntity = userMapper.mapRequestToEntity(userRequestDto);
        userEntity.setPassword(encoder.bCryptEncoder().encode(userRequestDto.password()));
        userRepository.save(userEntity);

        return userMapper.mapEntityToResponse(userEntity);
    }

    @Override
    @Transactional
    public UserResponseDto updateUser(UserRequestDto userRequestDto, String username) {
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found! : "));

        userEntity = userMapper.mapRequestToEntity(userRequestDto);
        userEntity.setPassword(encoder.bCryptEncoder().encode(userRequestDto.password()));
        userEntity.setUpdateDate(Instant.now());

        //userRepository.save(userEntity);

        return userMapper.mapEntityToResponse(userEntity);
    }

    @Override
    @Transactional
    public UserNameResponseDto updateName(UserNameRequestDto userNameRequestDto, String username) {
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));

        userEntity.setFirstName(userNameRequestDto.firstName());
        userEntity.setLastName(userNameRequestDto.lastName());
        userEntity.setUpdateDate(Instant.now());

        //userRepository.save(userEntity);

        return userMapper.mapEntityToNameResponse(userEntity);
    }

    @Override
    @Transactional
    public void deleteByUsername(String username) {
        if (!userRepository.existsByUsername(username)) {
            throw new UserNotFoundException("User not found!");
        }

        userRepository.deleteByUsername(username);
    }
}
