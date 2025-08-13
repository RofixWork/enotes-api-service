package com.rofix.enotes_service.service;

import com.rofix.enotes_service.dto.request.RegisterUserDTO;

public interface AuthService {
    String register(RegisterUserDTO registerUserDTO);
}
