package com.phucth42.identity_service.dto.request;

import com.phucth42.identity_service.validator.DobConstraint;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    @Size(min = 6 , max = 20, message = "USERNAME_INVALID")
    String username;
    String password;
    String firstName;
    String lastName;
    @DobConstraint(min = 20)
    LocalDate dateOfBirth;
}
