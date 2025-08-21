package com.rofix.enotes_service.exception.base;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends CustomException{
    public UnauthorizedException(String message)
    {
        super(message, HttpStatus.UNAUTHORIZED);
    }
}
