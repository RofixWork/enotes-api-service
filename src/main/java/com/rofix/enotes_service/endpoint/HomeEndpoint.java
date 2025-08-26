package com.rofix.enotes_service.endpoint;

import com.rofix.enotes_service.dto.request.RequestPasswordReset;
import com.rofix.enotes_service.dto.request.ResetPasswordDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User Password Management", description = "Manages password reset and update operations.")
@RequestMapping(value = "/api/v1/home", produces = MediaType.APPLICATION_JSON_VALUE)
public interface HomeEndpoint {

    @Operation(
            summary = "Request Password Reset",
            tags = {"User Password Management"},
            description = "Sends a password reset link to the user's email."
    )
    @PostMapping(value = "/request-reset-password", consumes =  MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> sendRequestPasswordReset(
            @Valid @RequestBody RequestPasswordReset requestPasswordReset,
            HttpServletRequest httpServletRequest
    );

    @Operation(
            summary = "Verify Password Reset Link",
            tags = {"User Password Management"},
            description = "Validates the unique token from the password reset email."
    )
    @GetMapping("/password/reset")
    ResponseEntity<?> verifyRequestPasswordReset(
            @Min(1) @RequestParam(name = "uid") Long userId,
            @NotBlank @RequestParam(name = "code") String code
    );

    @Operation(
            summary = "Reset Password",
            tags = {"User Password Management"},
            description = "Sets a new password after token verification."
    )
    @PostMapping("/password/reset")
    ResponseEntity<?> resetPassword(
            @Min (1) @RequestParam(name = "uid") Long userId,
            @NotBlank @RequestParam(name = "code") String code,
            @Valid @RequestBody ResetPasswordDTO resetPasswordDTO
    );
}