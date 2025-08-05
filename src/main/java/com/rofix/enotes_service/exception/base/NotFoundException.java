package com.rofix.enotes_service.exception.base;

import org.springframework.http.HttpStatus;

public class NotFoundException extends CustomException{
    public NotFoundException(String message)
    {
        super(message, HttpStatus.NOT_FOUND);
    }
}
