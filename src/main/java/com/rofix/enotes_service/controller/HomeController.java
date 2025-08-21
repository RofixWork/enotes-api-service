package com.rofix.enotes_service.controller;

import com.rofix.enotes_service.dto.request.RequestPasswordReset;
import com.rofix.enotes_service.dto.request.ResetPasswordDTO;
import com.rofix.enotes_service.service.HomeService;
import com.rofix.enotes_service.utils.ResponseUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/home", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
public class HomeController {
    private final HomeService homeService;

    @PostMapping(value = "/request-reset-password", consumes =  MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> sendRequestPasswordReset(
            @Valid @RequestBody RequestPasswordReset requestPasswordReset,
            HttpServletRequest httpServletRequest
    )
    {
        homeService.sendRequestPasswordReset(requestPasswordReset, httpServletRequest);

        return ResponseUtils.createSuccessResponse("Successfully send reset send request reset password");
    }

    @GetMapping("/password/reset")
    public ResponseEntity<?> verifyRequestPasswordReset(
            @Min (1) @RequestParam(name = "uid") Long userId,
            @NotBlank @RequestParam(name = "code") String code
    )
    {
        homeService.verifyRequestPasswordReset(userId, code);

        return ResponseUtils.createSuccessResponse("Successfully verified request reset password");
    }

    @PostMapping("/password/reset")
    public ResponseEntity<?> resetPassword(
            @Min (1) @RequestParam(name = "uid") Long userId,
            @NotBlank @RequestParam(name = "code") String code,
            @Valid @RequestBody ResetPasswordDTO resetPasswordDTO
    )
    {
        homeService.resetPassword(resetPasswordDTO, userId, code);

        return ResponseUtils.createSuccessResponse("Successfully Reset Password");
    }
}
