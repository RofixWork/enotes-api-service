package com.rofix.enotes_service.exception;

import com.rofix.enotes_service.exception.base.BadRequestException;
import com.rofix.enotes_service.exception.base.ConflictException;
import com.rofix.enotes_service.exception.base.NotFoundException;
import com.rofix.enotes_service.exception.base.UnauthorizedException;
import com.rofix.enotes_service.response.APIResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
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
        log.error("[GlobalExceptionHandler] :: [handleMethodArgumentNotValidException] :: {}", ex.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<APIResponse<Void>> handleConstraintViolationException(ConstraintViolationException ex) {
        log.error("[GlobalExceptionHandler] :: [handleConstraintViolationException] :: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(getAPIResponse(ex));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<APIResponse<Void>> handleNotFoundException(NotFoundException ex){
        log.error("[GlobalExceptionHandler] :: [handleNotFoundException] :: {}", ex.getMessage());
        return ResponseEntity.status(ex.getStatus()).body(getAPIResponse(ex));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<APIResponse<Void>> handleBadRequestException(BadRequestException ex){
        log.error("[GlobalExceptionHandler] :: [handleBadRequestException] :: {}", ex.getMessage());
        return ResponseEntity.status(ex.getStatus()).body(getAPIResponse(ex));
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<APIResponse<Void>> handleConflictException(ConflictException ex){
        log.error("[GlobalExceptionHandler] :: [handleConflictException] :: {}", ex.getMessage());
        return ResponseEntity.status(ex.getStatus()).body(getAPIResponse(ex));
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<APIResponse<Void>> handleUnauthorizedException(UnauthorizedException ex){
        log.error("[GlobalExceptionHandler] :: [handleUnauthorizedException] :: {}", ex.getMessage());
        return ResponseEntity.status(ex.getStatus()).body(getAPIResponse(ex));
    }

    private APIResponse<Void> getAPIResponse(Exception ex){
        return APIResponse.<Void>builder().message(ex.getMessage()).status(false).build();
    }
}
