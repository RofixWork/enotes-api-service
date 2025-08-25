package com.rofix.enotes_service.endpoint;

import com.rofix.enotes_service.dto.request.ChangePasswordDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/user")
public interface UserEndpoint {
    @GetMapping
    ResponseEntity<?> getProfile();

    @PostMapping("/change-password")
    ResponseEntity<?> changePassword(
            @Valid @RequestBody ChangePasswordDTO changePasswordDTO
    );
}
