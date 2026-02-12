package com.cybernetics.user_management_ms.service;

import com.cybernetics.user_management_ms.dto.request.UserNameRequestDto;
import com.cybernetics.user_management_ms.dto.request.UserRequestDto;
import com.cybernetics.user_management_ms.dto.response.UserNameResponseDto;
import com.cybernetics.user_management_ms.dto.response.UserResponseDto;

import java.util.List;

public interface UserService {

    List<UserResponseDto> getAllUsers();

    UserResponseDto findByUsername(String username);

    List<UserResponseDto> findByFirstNameAndLastNameIgnoreCase(String firstName, String lastName);

    UserResponseDto createUser(UserRequestDto userRequestDto);

    UserResponseDto updateUser(UserRequestDto userRequestDto, String username);

    UserNameResponseDto updateName(UserNameRequestDto userNameRequestDto, String username);

    void deleteByUsername(String username);

}
