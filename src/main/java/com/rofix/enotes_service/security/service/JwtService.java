package com.rofix.enotes_service.security.service;

import com.rofix.enotes_service.entity.User;
import jakarta.servlet.http.HttpServletRequest;

public interface JwtService {
    String generateToken(User user);

    String getUsernameFromToken(String token);

    String getTokenFromHeader(HttpServletRequest request);

    void validateToken(String token);
}
