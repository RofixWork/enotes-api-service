package com.rofix.enotes_service.endpoint;

import com.rofix.enotes_service.dto.request.LoginUserRequestDTO;
import com.rofix.enotes_service.dto.request.RegisterUserDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/api/v1/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public interface AuthEndpoint {
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> register(
            @Valid @RequestBody RegisterUserDTO registerUserDTO,
            HttpServletRequest request
    );

    @PostMapping("/login")
    ResponseEntity<?> login(
            @Valid @RequestBody LoginUserRequestDTO loginUserRequestDTO
    );

    @GetMapping("/verify")
    ResponseEntity<?> verify(
            @RequestParam("uid") Long userId,
            @RequestParam("vc") String verificationCode
    );
}
