package com.rofix.enotes_service.endpoint;

import com.rofix.enotes_service.dto.request.LoginUserRequestDTO;
import com.rofix.enotes_service.dto.request.RegisterUserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Authentication", description = "Handles user registration, login, and account verification.")
@RequestMapping(value = "/api/v1/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public interface AuthEndpoint {
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "409", description = "Conflict"),
            @ApiResponse(responseCode = "500", description = "Internal Server")
    }
    )
    @Operation(
            summary = "Register a new user",
            description = "Creates a new user account and sends a verification email.",
            tags = {"Authentication", "User Password Management"}
    )
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> register(
            @Valid @RequestBody RegisterUserDTO registerUserDTO,
            HttpServletRequest request
    );

    @Operation(
            summary = "User login",
            description = "Authenticates a user and issues a security token upon successful login.",
            tags = {"Authentication", "User Password Management", "User"}
    )
    @PostMapping("/login")
    ResponseEntity<?> login(
            @Valid @RequestBody LoginUserRequestDTO loginUserRequestDTO
    );

    @Operation(
            summary = "Verify user account",
            description = "Activates a user's account using a unique verification code from the registration email.",
            tags = {"Authentication", "User Password Management"}
    )
    @GetMapping("/verify")
    ResponseEntity<?> verify(
            @RequestParam("uid") Long userId,
            @RequestParam("vc") String verificationCode
    );
}