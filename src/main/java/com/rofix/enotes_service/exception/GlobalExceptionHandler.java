package com.rofix.enotes_service.exception;

import com.rofix.enotes_service.exception.base.BadRequestException;
import com.rofix.enotes_service.exception.base.ConflictException;
import com.rofix.enotes_service.exception.base.NotFoundException;
import com.rofix.enotes_service.exception.base.UnauthorizedException;
import com.rofix.enotes_service.helper.LoggerHelper;
import com.rofix.enotes_service.utils.ResponseUtils;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.event.Level;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestControllerAdvice
public class GlobalExceptionHandler {
    private final LoggerHelper loggerHelper;

    public GlobalExceptionHandler(LoggerHelper loggerHelper) {
        this.loggerHelper = loggerHelper;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<Map<String, String>> errors = ex.getBindingResult().getAllErrors().stream().map((error) -> {
            Map<String, String> map = new HashMap<>();
            String fieldName = ((FieldError) error).getField(),
                    fieldValue = error.getDefaultMessage();
            map.put("field", fieldName);
            map.put("message", fieldValue);
            return map;
        }).toList();

        Map<String, Object> response = new HashMap<>(Map.of("errors", errors));

        loggerHelper.createLog(Level.ERROR, GlobalExceptionHandler.class.getName(), "handleMethodArgumentNotValidException", ex.getMessage());
        return ResponseUtils.createErrorResponse(HttpStatus.BAD_REQUEST, response);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException ex) {
        loggerHelper.createLog(Level.ERROR, GlobalExceptionHandler.class.getName(), "handleConstraintViolationException", ex.getMessage());
        return ResponseUtils.createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(NotFoundException ex){
        loggerHelper.createLog(Level.ERROR, GlobalExceptionHandler.class.getName(), "handleNotFoundException", ex.getMessage());
        return ResponseUtils.createErrorResponse(ex.getStatus(), ex.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handleBadRequestException(BadRequestException ex){
        loggerHelper.createLog(Level.ERROR, GlobalExceptionHandler.class.getName(), "handleBadRequestException", ex.getMessage());
        return ResponseUtils.createErrorResponse(ex.getStatus(), ex.getMessage());
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<?> handleConflictException(ConflictException ex){
        loggerHelper.createLog(Level.ERROR, GlobalExceptionHandler.class.getName(), "handleConflictException", ex.getMessage());
        return ResponseUtils.createErrorResponse(ex.getStatus(), ex.getMessage());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<?> handleUnauthorizedException(UnauthorizedException ex){
        loggerHelper.createLog(Level.ERROR, GlobalExceptionHandler.class.getName(), "handleUnauthorizedException", ex.getMessage());
        return ResponseUtils.createErrorResponse(ex.getStatus(), ex.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public  ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex){
        loggerHelper.createLog(Level.ERROR, GlobalExceptionHandler.class.getName(), "handleHttpMessageNotReadableException", ex.getMessage());
        return ResponseUtils.createErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }
}
