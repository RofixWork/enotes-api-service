package com.rofix.enotes_service.service;

import com.rofix.enotes_service.dto.request.RequestPasswordReset;
import com.rofix.enotes_service.dto.request.ResetPasswordDTO;
import com.rofix.enotes_service.entity.AccountStatus;
import com.rofix.enotes_service.entity.User;
import com.rofix.enotes_service.exception.base.BadRequestException;
import com.rofix.enotes_service.helper.HomeHelper;
import com.rofix.enotes_service.repository.UserRepository;
import com.rofix.enotes_service.utils.LoggerUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.event.Level;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HomeServiceImpl implements HomeService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final HomeHelper homeHelper;

    @Override
    public void sendRequestPasswordReset(RequestPasswordReset requestPasswordReset, HttpServletRequest request) {
        User user = userRepository.findByEmail(requestPasswordReset.getEmail()).orElseThrow(() -> {
            LoggerUtils.createLog(Level.WARN, HomeService.class.getName(), "sendRequestPasswordReset", "Invalid email address.");
            return new BadRequestException("Invalid User!!!");
        });
        AccountStatus accountStatus = user.getStatus();
        String passwordResetCode = UUID.randomUUID().toString();

        accountStatus.setPasswordResetToken(passwordResetCode);
        accountStatus.setPasswordResetExpiry(Instant.now().plus(Duration.ofMinutes(10L)));
        userRepository.save(user);

        homeHelper.sendResetPasswordLink(user, request);
    }

    @Override
    public void verifyRequestPasswordReset(Long userId, String code) {
        homeHelper.getUserByIdAndPasswordResetCode(userId, code);
    }


    @Override
    public void resetPassword(ResetPasswordDTO resetPasswordDTO, Long userId, String code) {

        if(!resetPasswordDTO.getPassword().equals(resetPasswordDTO.getConfirmPassword())) {
            throw new BadRequestException("Passwords do not match");
        }

        User user = homeHelper.getUserByIdAndPasswordResetCode(userId, code);
        AccountStatus accountStatus = user.getStatus();

        user.setPassword(passwordEncoder.encode(resetPasswordDTO.getPassword()));
        accountStatus.setPasswordResetToken(null);
        accountStatus.setPasswordResetExpiry(null);

        userRepository.save(user);

    }
}
