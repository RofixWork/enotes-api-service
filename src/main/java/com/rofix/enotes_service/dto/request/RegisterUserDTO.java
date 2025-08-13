package com.rofix.enotes_service.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterUserDTO {
    @NotBlank(message = "First name is required.")
    @Size(min = 3, max = 50, message = "First name must be between 3 and 50 characters.")
    @Pattern(regexp = "^[a-zA-Z]{3,50}$", message = "First name must contain only alphabetic characters.")
    private String firstName;

    @NotBlank(message = "Last name is required.")
    @Size(min = 3, max = 50, message = "Last name must be between 3 and 50 characters.")
    @Pattern(regexp = "^[a-zA-Z]{3,50}$", message = "Last name must contain only alphabetic characters.")
    private String lastName;

    @NotBlank(message = "Email is required.")
    @Email(message = "Invalid email format.")
    private String email;

    @NotBlank(message = "Password is required.")
    @Size(min = 6, message = "Password must be at least 6 characters long.")
    private String password;

    @Pattern(regexp = "^(\\+|00)[1-9][0-9 \\-\\(\\)\\.]{7,32}$", message = "Invalid mobile number format. Please include the country code (e.g., +212).")
    private String mobileNo;

    @NotEmpty(message = "User must have at least one role (admin | user).")
    private Set<@Pattern(regexp = "^(?i)(admin|user)$", message = "Role must be either 'admin' or 'user' only.") String> roles;
}