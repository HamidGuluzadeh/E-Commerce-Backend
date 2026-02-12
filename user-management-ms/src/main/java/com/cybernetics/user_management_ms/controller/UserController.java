package com.cybernetics.user_management_ms.controller;

import com.cybernetics.user_management_ms.dto.SuccessDto;
import com.cybernetics.user_management_ms.dto.request.UserNameRequestDto;
import com.cybernetics.user_management_ms.dto.request.UserRequestDto;
import com.cybernetics.user_management_ms.dto.response.UserNameResponseDto;
import com.cybernetics.user_management_ms.dto.response.UserResponseDto;
import com.cybernetics.user_management_ms.service.implement.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.cybernetics.user_management_ms.utils.SuccessStatus.SUCCESS;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserServiceImpl userService;

    @GetMapping("/allUsers")
    public ResponseEntity<SuccessDto<List<UserResponseDto>>> getAllUsers() {
        List<UserResponseDto> userResponseDto = userService.getAllUsers();
        SuccessDto<List<UserResponseDto>> userResponseSuccessDto = new SuccessDto<>(SUCCESS, userResponseDto);
        return new ResponseEntity<>(userResponseSuccessDto,HttpStatus.OK);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<SuccessDto<UserResponseDto>> findByUsername(@PathVariable String username) {
        UserResponseDto userResponseDto = userService.findByUsername(username);
        SuccessDto<UserResponseDto> userResponseSuccessDto = new SuccessDto<>(SUCCESS, userResponseDto);
        return new ResponseEntity<>(userResponseSuccessDto, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<SuccessDto<List<UserResponseDto>>> findByFirstNameAndLastNameIgnoreCase(@RequestParam(required = false) String firstName,
                                                                                    @RequestParam(required = false) String lastName) {
        List<UserResponseDto> userResponseDto = userService.findByFirstNameAndLastNameIgnoreCase(firstName, lastName);
        SuccessDto<List<UserResponseDto>> userResponseSuccessDto = new SuccessDto<>(SUCCESS, userResponseDto);
        return new ResponseEntity<>(userResponseSuccessDto, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<SuccessDto<UserResponseDto>> createUser(@RequestBody @Valid UserRequestDto userRequestDto) {
        UserResponseDto userResponseDto = userService.createUser(userRequestDto);
        SuccessDto<UserResponseDto> userResponseSuccessDto = new SuccessDto<>(SUCCESS, userResponseDto);
        return new ResponseEntity<>(userResponseSuccessDto, HttpStatus.CREATED);
    }

    @PutMapping("/user/{username}")
    public ResponseEntity<SuccessDto<UserResponseDto>> updateUser(@RequestBody @Valid UserRequestDto userRequestDto,
                                                                  @PathVariable String username) {
        UserResponseDto userResponseDto = userService.updateUser(userRequestDto, username);
        SuccessDto<UserResponseDto> userResponseSuccessDto = new SuccessDto<>(SUCCESS, userResponseDto);
        return new ResponseEntity<>(userResponseSuccessDto, HttpStatus.OK);
    }

    @PatchMapping("/user/{username}/name")
    public ResponseEntity<SuccessDto<UserNameResponseDto>> updateName(@RequestBody @Valid UserNameRequestDto userNameRequestDto,
                                                                      @PathVariable String username) {
        UserNameResponseDto userNameResponseDto = userService.updateName(userNameRequestDto, username);
        SuccessDto<UserNameResponseDto> userNameResponseSuccessDto = new SuccessDto<>(SUCCESS, userNameResponseDto);
        return new ResponseEntity<>(userNameResponseSuccessDto, HttpStatus.OK);
    }

    @DeleteMapping("/user/{username}")
    public ResponseEntity<Void> deleteUser(@PathVariable String username) {
        userService.deleteByUsername(username);
        return ResponseEntity.noContent().build();
    }
}
