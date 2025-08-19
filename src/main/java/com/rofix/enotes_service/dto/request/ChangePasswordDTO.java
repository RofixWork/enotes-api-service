package com.rofix.enotes_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
public class ChangePasswordDTO {
    @NotBlank(message = "Old Password is required.")
    @Size(min = 6, message = "Old Password must be at least 6 characters long.")
    private String oldPassword;

    @NotBlank(message = "New Password is required.")
    @Size(min = 6, message = "New Password must be at least 6 characters long.")
    private String newPassword;
}
