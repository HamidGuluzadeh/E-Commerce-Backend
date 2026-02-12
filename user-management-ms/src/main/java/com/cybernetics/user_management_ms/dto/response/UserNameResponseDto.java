package com.cybernetics.user_management_ms.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserNameResponseDto {
    String username;
    String firstName;
    String lastName;
    Instant updateDate;
}
