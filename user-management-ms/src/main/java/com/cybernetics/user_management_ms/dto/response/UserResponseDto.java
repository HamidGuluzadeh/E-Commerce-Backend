package com.cybernetics.user_management_ms.dto.response;

import com.cybernetics.user_management_ms.utils.UserRole;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponseDto {
    String username;
    String firstName;
    String lastName;
    String email;
    UserRole userRole;
}
