package com.rofix.enotes_service.utils;

import com.rofix.enotes_service.dto.response.GenericResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ResponseUtils {
    public static ResponseEntity<?> createSuccessResponse(
            HttpStatus statusCode,
            String message,
            Object data
    )
    {
        GenericResponse genericResponse = GenericResponse.builder()
                .httpStatus(statusCode)
                .message(message)
                .data(data)
                .build();

        return genericResponse.create();
    }

    public static ResponseEntity<?> createSuccessResponse(
            String message,
            Object data
    )
    {
        GenericResponse genericResponse = GenericResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(message)
                .data(data)
                .build();

        return genericResponse.create();
    }

    public static ResponseEntity<?> createSuccessResponse(
            HttpStatus statusCode,
            String message
    )
    {
        GenericResponse genericResponse = GenericResponse.builder()
                .httpStatus(statusCode)
                .message(message)
                .build();

        return genericResponse.create();
    }

    public static ResponseEntity<?> createSuccessResponse(
            String message
    )
    {
        GenericResponse genericResponse = GenericResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(message)
                .build();

        return genericResponse.create();
    }

    public static ResponseEntity<?> createErrorResponse(
            HttpStatus statusCode,
            String message
    )
    {
        GenericResponse genericResponse = GenericResponse.builder()
                .httpStatus(statusCode)
                .status(false)
                .message(message)
                .build();

        return genericResponse.create();
    }

    public static ResponseEntity<?> createErrorResponse(
            HttpStatus statusCode,
            Object data
    )
    {
        GenericResponse genericResponse = GenericResponse.builder()
                .httpStatus(statusCode)
                .status(false)
                .data(data)
                .build();

        return genericResponse.create();
    }
}
