package com.rofix.enotes_service.controller;

import com.rofix.enotes_service.dto.request.ChangePasswordDTO;
import com.rofix.enotes_service.dto.response.UserResponseDTO;
import com.rofix.enotes_service.entity.User;
import com.rofix.enotes_service.service.UserService;
import com.rofix.enotes_service.utils.AuthUtils;
import com.rofix.enotes_service.utils.ResponseUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping
    public ResponseEntity<?> getProfile()
    {
        User currentUser = AuthUtils.getLoggedInUser();

        UserResponseDTO userResponseDTO = userService.getCurrentUser(currentUser);

        return ResponseUtils.createSuccessResponse("Current User", userResponseDTO);
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(
            @Valid @RequestBody ChangePasswordDTO changePasswordDTO
    ) {
        User currentUser = AuthUtils.getLoggedInUser();
        String status = userService.changePassword(currentUser, changePasswordDTO);
        return ResponseUtils.createSuccessResponse(status);
    }
}
