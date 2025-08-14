package com.rofix.enotes_service.service;

import com.rofix.enotes_service.dto.request.LoginUserRequestDTO;
import com.rofix.enotes_service.dto.request.RegisterUserDTO;
import com.rofix.enotes_service.dto.response.LoginUserResponseDTO;
import jakarta.validation.Valid;

public interface AuthService {
    String register(RegisterUserDTO registerUserDTO);

    String verifyUserAccount(Long userId, String verificationCode);

    LoginUserResponseDTO loginUser(@Valid LoginUserRequestDTO loginUserRequestDTO);
}
