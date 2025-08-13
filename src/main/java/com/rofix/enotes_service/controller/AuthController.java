package com.rofix.enotes_service.controller;

import com.rofix.enotes_service.dto.request.RegisterUserDTO;
import com.rofix.enotes_service.service.AuthService;
import com.rofix.enotes_service.utils.ResponseUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/auth", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @Valid @RequestBody RegisterUserDTO registerUserDTO
    )
    {
        String status = authService.register(registerUserDTO);

        return ResponseUtils.createSuccessResponse(HttpStatus.CREATED, status);
    }
}
