package com.rofix.enotes_service.security.service;

import com.rofix.enotes_service.entity.User;

public interface JwtService {
    String generateToken(User user);
}
