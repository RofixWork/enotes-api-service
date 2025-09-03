package com.rofix.enotes_service.exception.base;

import jakarta.validation.ConstraintViolation;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.Set;

@Getter
@Setter
public class CustomValidationException extends CustomException {
    private final transient Set<? extends ConstraintViolation<?>> constraintViolation;

    public CustomValidationException(Set<? extends ConstraintViolation<?>> constraintViolation) {
        super("Validation Failed", HttpStatus.BAD_REQUEST);
        this.constraintViolation = constraintViolation;
    }
}
