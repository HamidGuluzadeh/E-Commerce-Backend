package com.cybernetics.user_management_ms.entity;

import com.cybernetics.user_management_ms.utils.UserRole;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id", length = 36, nullable = false, updatable = false)
    String userId;

    @Column(name = "username", length = 30, nullable = false, unique = true)
    String username;

    @Column(name = "first_name", length = 20, nullable = false)
    String firstName;

    @Column(name = "last_name", length = 30)
    String lastName;

    @Column(name = "user_role", nullable = false)
    @Enumerated(EnumType.STRING)
    UserRole userRole;

    @Column(name = "user_password", nullable = false)
    String password;

    @Column(name = "phone_number", unique = true, nullable = false)
    String phoneNumber;

    @Column(name = "user_email", nullable = false, unique = true)
    String email;

    @Column(name = "user_birthdate", nullable = false)
    LocalDate birthdate;

    @Column(name = "create_date", nullable = false, updatable = false)
    @CreationTimestamp
    Instant createDate;

    @Column(name = "update_date")
    Instant updateDate;
}
