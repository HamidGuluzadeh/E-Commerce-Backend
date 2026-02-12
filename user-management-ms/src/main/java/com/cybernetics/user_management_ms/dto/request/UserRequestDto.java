package com.cybernetics.user_management_ms.dto.request;

import com.cybernetics.user_management_ms.utils.UserRole;
import jakarta.validation.constraints.*;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record UserRequestDto(@NotBlank String username,
                             @NotBlank String firstName,
                             String lastName,
                             @NotNull UserRole userRole,
                             @NotBlank @Size(min = 8, message = "Password must contain at least 8 characters!")
                             String password,
                             String phoneNumber,
                             @NotBlank @Email String email,
                             @NotNull @Past LocalDate birthdate) {
}
