package com.rofix.enotes_service.service;

import com.rofix.enotes_service.dto.request.EmailDetailsDTO;

public interface EmailSendService {
    void send(EmailDetailsDTO emailDetails);
}
