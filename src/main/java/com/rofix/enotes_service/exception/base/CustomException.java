package com.rofix.enotes_service.exception.base;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public abstract class CustomException extends RuntimeException {
    @Getter
    private final HttpStatus status;

    protected CustomException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
