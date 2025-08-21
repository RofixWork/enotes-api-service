package com.rofix.enotes_service.helper;

import com.rofix.enotes_service.config.AppConstants;
import com.rofix.enotes_service.dto.request.EmailDetailsDTO;
import com.rofix.enotes_service.entity.User;
import com.rofix.enotes_service.exception.base.BadRequestException;
import com.rofix.enotes_service.repository.UserRepository;
import com.rofix.enotes_service.service.EmailSendService;
import com.rofix.enotes_service.service.HomeService;
import com.rofix.enotes_service.utils.LoggerUtils;
import com.rofix.enotes_service.utils.UrlUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.event.Level;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@RequiredArgsConstructor
public class HomeHelper {
    private final UserRepository userRepository;
    private final EmailSendService emailSendService;

    public void sendResetPasswordLink(User user, HttpServletRequest request) {
        String baseURL = UrlUtils.getBaseUrl(request);
        String resetLink = String.format("%s/api/v1/home/password/reset?uid=%d&code=%s", baseURL, user.getId(), user.getStatus().getPasswordResetToken());
        String message = String.format(AppConstants.TEMPLATE_RESET_PASSWORD, user.getFirstName(), resetLink, resetLink);

        EmailDetailsDTO emailDetailsDTO = EmailDetailsDTO.builder()
                .to(user.getEmail())
                .title("Request Reset Password")
                .subject("Reset Password")
                .message(message)
                .build();

        LoggerUtils.createLog(Level.INFO, HomeService.class.getName(), "sendResetPasswordLink", "Request Reset Password send successfully...");
        emailSendService.send(emailDetailsDTO);
    }

    public User getUserByIdAndPasswordResetCode(Long userId, String code) {
        User user = userRepository.findByIdAndPasswordResetToken(userId, code).orElseThrow(() -> {
            LoggerUtils.createLog(Level.WARN, HomeService.class.getName(), "verifyRequestPasswordReset", "Invalid User or Code");
            return new BadRequestException("Invalid Password Reset Token or Expired!!!");
        });

        if(user.getStatus().getPasswordResetExpiry().isBefore(Instant.now()))
        {
            LoggerUtils.createLog(Level.WARN, HomeService.class.getName(), "verifyRequestPasswordReset", "Invalid User or Code");
            throw new BadRequestException("Invalid Password Reset Token or Expired!!!");
        }

        return user;
    }
}
