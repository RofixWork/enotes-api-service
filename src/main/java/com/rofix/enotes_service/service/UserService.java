package com.rofix.enotes_service.service;

import com.rofix.enotes_service.dto.response.UserResponseDTO;
import com.rofix.enotes_service.entity.User;

public interface UserService {
    UserResponseDTO getCurrentUser(User currentUser);
}
