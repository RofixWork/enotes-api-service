package com.rofix.enotes_service.service;

import com.rofix.enotes_service.dto.request.EmailDetailsDTO;
import com.rofix.enotes_service.exception.base.BadRequestException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSendServiceImpl implements EmailSendService{
    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String mailSender;

    @Override
    public void send(EmailDetailsDTO emailDetails) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper;
        try
        {
            helper = new MimeMessageHelper(message, true);

            helper.setFrom(mailSender, emailDetails.getTitle());
            helper.setTo(emailDetails.getTo());
            helper.setSubject(emailDetails.getSubject());
            helper.setText(emailDetails.getMessage(), true);

            javaMailSender.send(message);

        }catch (Exception ex)
        {
            throw new BadRequestException(ex.getMessage());
        }
    }
}
