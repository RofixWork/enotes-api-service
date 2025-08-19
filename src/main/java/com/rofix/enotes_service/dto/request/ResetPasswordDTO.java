package com.rofix.enotes_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordDTO {
    @NotBlank(message = "Password is required.")
    @Size(min = 6, message = "Password must be at least 6 characters long.")
    private String password;

    @NotBlank(message = "Confirm Password is required.")
    @Size(min = 6, message = "Confirm Password must be at least 6 characters long.")
    private String confirmPassword;
}
