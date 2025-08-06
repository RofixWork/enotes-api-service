package com.rofix.enotes_service.exception;

import com.rofix.enotes_service.exception.base.BadRequestException;
import com.rofix.enotes_service.exception.base.ConflictException;
import com.rofix.enotes_service.exception.base.NotFoundException;
import com.rofix.enotes_service.exception.base.UnauthorizedException;
import com.rofix.enotes_service.helper.LoggerHelper;
import com.rofix.enotes_service.response.APIResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
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
    public ResponseEntity<List<Map<String, String>>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<Map<String, String>> errors = ex.getBindingResult().getAllErrors().stream().map((error) -> {
            Map<String, String> map = new HashMap<>();
            String fieldName = ((FieldError) error).getField(),
                    fieldValue = error.getDefaultMessage();
            map.put("field", fieldName);
            map.put("message", fieldValue);
            return map;
        }).toList();
        loggerHelper.createLog(Level.ERROR, GlobalExceptionHandler.class.getName(), "handleMethodArgumentNotValidException", ex.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<APIResponse<Void>> handleConstraintViolationException(ConstraintViolationException ex) {
        loggerHelper.createLog(Level.ERROR, GlobalExceptionHandler.class.getName(), "handleConstraintViolationException", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(getAPIResponse(ex));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<APIResponse<Void>> handleNotFoundException(NotFoundException ex){
        loggerHelper.createLog(Level.ERROR, GlobalExceptionHandler.class.getName(), "handleNotFoundException", ex.getMessage());
        return ResponseEntity.status(ex.getStatus()).body(getAPIResponse(ex));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<APIResponse<Void>> handleBadRequestException(BadRequestException ex){
        loggerHelper.createLog(Level.ERROR, GlobalExceptionHandler.class.getName(), "handleBadRequestException", ex.getMessage());
        return ResponseEntity.status(ex.getStatus()).body(getAPIResponse(ex));
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<APIResponse<Void>> handleConflictException(ConflictException ex){
        loggerHelper.createLog(Level.ERROR, GlobalExceptionHandler.class.getName(), "handleConflictException", ex.getMessage());
        return ResponseEntity.status(ex.getStatus()).body(getAPIResponse(ex));
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<APIResponse<Void>> handleUnauthorizedException(UnauthorizedException ex){
        loggerHelper.createLog(Level.ERROR, GlobalExceptionHandler.class.getName(), "handleUnauthorizedException", ex.getMessage());
        return ResponseEntity.status(ex.getStatus()).body(getAPIResponse(ex));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public  ResponseEntity<APIResponse<Void>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex){
        loggerHelper.createLog(Level.ERROR, GlobalExceptionHandler.class.getName(), "handleHttpMessageNotReadableException", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(getAPIResponse(ex));
    }

    private APIResponse<Void> getAPIResponse(Exception ex){
        return APIResponse.<Void>builder().message(ex.getMessage()).status(false).build();
    }

}
