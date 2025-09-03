package com.rofix.enotes_service.service;

import com.rofix.enotes_service.dto.request.ChangePasswordDTO;
import com.rofix.enotes_service.dto.response.UserResponseDTO;
import com.rofix.enotes_service.entity.User;

public interface UserService {
    UserResponseDTO getCurrentUser(User currentUser);

    String changePassword(User currentUser, ChangePasswordDTO changePasswordDTO);
}
