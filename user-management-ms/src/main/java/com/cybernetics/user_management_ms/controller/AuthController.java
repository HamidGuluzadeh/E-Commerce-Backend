package com.cybernetics.user_management_ms.controller;

import com.cybernetics.user_management_ms.dto.SuccessDto;
import com.cybernetics.user_management_ms.dto.request.LoginRequestDto;
import com.cybernetics.user_management_ms.dto.request.RefreshTokenRequestDto;
import com.cybernetics.user_management_ms.dto.response.AuthResponseDto;
import com.cybernetics.user_management_ms.service.implement.AuthServiceImpl;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.cybernetics.user_management_ms.utils.SuccessStatus.SUCCESS;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {
    AuthServiceImpl authService;

    @PostMapping("/login")
    public ResponseEntity<SuccessDto<AuthResponseDto>> login(@RequestBody @Valid LoginRequestDto loginRequestDto) {
        AuthResponseDto authResponseDto = authService.login(loginRequestDto);
        SuccessDto<AuthResponseDto> authResponseSuccessDto = new SuccessDto<>(SUCCESS, authResponseDto);
        return new ResponseEntity<>(authResponseSuccessDto, HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<SuccessDto<AuthResponseDto>> refresh(@RequestBody @Valid RefreshTokenRequestDto refreshTokenRequestDto) {
        AuthResponseDto authResponseDto = authService.refresh(refreshTokenRequestDto);
        SuccessDto<AuthResponseDto> authResponseSuccessDto = new SuccessDto<>(SUCCESS, authResponseDto);
        return new ResponseEntity<>(authResponseSuccessDto, HttpStatus.CREATED);
    }
}
