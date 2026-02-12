package com.cybernetics.user_management_ms.mapper;

import com.cybernetics.user_management_ms.dto.request.UserRequestDto;
import com.cybernetics.user_management_ms.dto.response.UserNameResponseDto;
import com.cybernetics.user_management_ms.dto.response.UserResponseDto;
import com.cybernetics.user_management_ms.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserEntity mapRequestToEntity(UserRequestDto userRequestDto);

    UserResponseDto mapEntityToResponse(UserEntity userEntity);

    UserNameResponseDto mapEntityToNameResponse(UserEntity userEntity);

}
