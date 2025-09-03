package com.rofix.enotes_service.service;

import com.rofix.enotes_service.dto.request.RequestPasswordReset;
import com.rofix.enotes_service.dto.request.ResetPasswordDTO;
import jakarta.servlet.http.HttpServletRequest;

public interface HomeService {
    void sendRequestPasswordReset(RequestPasswordReset requestPasswordReset, HttpServletRequest httpServletRequest);

    void verifyRequestPasswordReset(Long userId, String code);

    void resetPassword(ResetPasswordDTO resetPasswordDTO,Long userId, String code);
}
