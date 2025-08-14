package com.rofix.enotes_service.controller;

import com.rofix.enotes_service.dto.request.RegisterUserDTO;
import com.rofix.enotes_service.service.AuthService;
import com.rofix.enotes_service.utils.ResponseUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {
    private final AuthService authService;

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> register(
            @Valid @RequestBody RegisterUserDTO registerUserDTO
    )
    {
        String status = authService.register(registerUserDTO);

        return ResponseUtils.createSuccessResponse(HttpStatus.CREATED, status);
    }

    @GetMapping("/verify")
    public ResponseEntity<?> verify(
            @RequestParam("uid") Long userId,
            @RequestParam("vc") String verificationCode
    ) {
        String status = authService.verifyUserAccount(userId, verificationCode);

        return ResponseUtils.createSuccessResponse(status);
    }
}
