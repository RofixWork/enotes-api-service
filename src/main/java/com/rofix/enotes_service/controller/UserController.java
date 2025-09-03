package com.rofix.enotes_service.controller;

import com.rofix.enotes_service.dto.request.ChangePasswordDTO;
import com.rofix.enotes_service.dto.response.UserResponseDTO;
import com.rofix.enotes_service.endpoint.UserEndpoint;
import com.rofix.enotes_service.entity.User;
import com.rofix.enotes_service.service.UserService;
import com.rofix.enotes_service.utils.AuthUtils;
import com.rofix.enotes_service.utils.ResponseUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController implements UserEndpoint {
    private final UserService userService;

    @Override
    public ResponseEntity<?> getProfile()
    {
        User currentUser = AuthUtils.getLoggedInUser();

        UserResponseDTO userResponseDTO = userService.getCurrentUser(currentUser);

        return ResponseUtils.createSuccessResponse("Current User", userResponseDTO);
    }

    @Override
    public ResponseEntity<?> changePassword(
            ChangePasswordDTO changePasswordDTO
    ) {
        User currentUser = AuthUtils.getLoggedInUser();
        String status = userService.changePassword(currentUser, changePasswordDTO);
        return ResponseUtils.createSuccessResponse(status);
    }
}
