package com.rofix.enotes_service.controller;

import com.rofix.enotes_service.dto.request.RequestPasswordReset;
import com.rofix.enotes_service.dto.request.ResetPasswordDTO;
import com.rofix.enotes_service.endpoint.HomeEndpoint;
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
@RequiredArgsConstructor
@Validated
public class HomeController implements HomeEndpoint {
    private final HomeService homeService;

    @Override
    public ResponseEntity<?> sendRequestPasswordReset(
            RequestPasswordReset requestPasswordReset,
            HttpServletRequest httpServletRequest
    )
    {
        homeService.sendRequestPasswordReset(requestPasswordReset, httpServletRequest);

        return ResponseUtils.createSuccessResponse("Successfully send reset send request reset password");
    }

    @Override
    public ResponseEntity<?> verifyRequestPasswordReset(
           Long userId,
           String code
    )
    {
        homeService.verifyRequestPasswordReset(userId, code);

        return ResponseUtils.createSuccessResponse("Successfully verified request reset password");
    }

    @Override
    public ResponseEntity<?> resetPassword(
            Long userId,
            String code,
            ResetPasswordDTO resetPasswordDTO
    )
    {
        homeService.resetPassword(resetPasswordDTO, userId, code);

        return ResponseUtils.createSuccessResponse("Successfully Reset Password");
    }
}
