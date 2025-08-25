package com.rofix.enotes_service.controller;

import com.rofix.enotes_service.dto.request.LoginUserRequestDTO;
import com.rofix.enotes_service.dto.request.RegisterUserDTO;
import com.rofix.enotes_service.dto.response.LoginUserResponseDTO;
import com.rofix.enotes_service.endpoint.AuthEndpoint;
import com.rofix.enotes_service.service.AuthService;
import com.rofix.enotes_service.utils.ResponseUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthEndpoint {
    private final AuthService authService;

    public ResponseEntity<?> register(
            RegisterUserDTO registerUserDTO,
            HttpServletRequest request
    )
    {
        String status = authService.register(registerUserDTO, request);

        return ResponseUtils.createSuccessResponse(HttpStatus.CREATED, status);
    }

    @Override
    public ResponseEntity<?> login(
            LoginUserRequestDTO loginUserRequestDTO
    ) {
        LoginUserResponseDTO loginUserResponseDTO = authService.loginUser(loginUserRequestDTO);
        return ResponseUtils.createSuccessResponse("Login Success", loginUserResponseDTO);
    }

    @Override
    public ResponseEntity<?> verify(
           Long userId,
           String verificationCode
    ) {
        String status = authService.verifyUserAccount(userId, verificationCode);

        return ResponseUtils.createSuccessResponse(status);
    }
}
