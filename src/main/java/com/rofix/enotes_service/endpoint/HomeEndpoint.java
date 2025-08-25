package com.rofix.enotes_service.endpoint;

import com.rofix.enotes_service.dto.request.RequestPasswordReset;
import com.rofix.enotes_service.dto.request.ResetPasswordDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/api/v1/home", produces = MediaType.APPLICATION_JSON_VALUE)
public interface HomeEndpoint {
    @PostMapping(value = "/request-reset-password", consumes =  MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> sendRequestPasswordReset(
            @Valid @RequestBody RequestPasswordReset requestPasswordReset,
            HttpServletRequest httpServletRequest
    );

    @GetMapping("/password/reset")
    ResponseEntity<?> verifyRequestPasswordReset(
            @Min(1) @RequestParam(name = "uid") Long userId,
            @NotBlank @RequestParam(name = "code") String code
    );

    @PostMapping("/password/reset")
    ResponseEntity<?> resetPassword(
            @Min (1) @RequestParam(name = "uid") Long userId,
            @NotBlank @RequestParam(name = "code") String code,
            @Valid @RequestBody ResetPasswordDTO resetPasswordDTO
    );
}
