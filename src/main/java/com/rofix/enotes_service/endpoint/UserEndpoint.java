package com.rofix.enotes_service.endpoint;

import com.rofix.enotes_service.dto.request.ChangePasswordDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "User", description = "Manages user-related operations like profile retrieval and password changes.")
@RequestMapping("/api/v1/user")
public interface UserEndpoint {

    @Operation(
            summary = "Get user profile",
            description = "Retrieves the profile information for the authenticated user.",
            tags = {"User"}
    )
    @GetMapping
    ResponseEntity<?> getProfile();

    @Operation(
            summary = "Change user password",
            description = "Allows the authenticated user to change their password.",
            tags = {"User"}
    )
    @PostMapping("/change-password")
    ResponseEntity<?> changePassword(
            @Valid @RequestBody ChangePasswordDTO changePasswordDTO
    );
}
