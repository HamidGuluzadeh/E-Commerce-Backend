package com.cybernetics.user_management_ms.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UserNameRequestDto(@NotBlank String firstName,
                                 String lastName) {
}
