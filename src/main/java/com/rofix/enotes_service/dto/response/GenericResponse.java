package com.rofix.enotes_service.dto.response;

import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GenericResponse {
    private HttpStatus httpStatus;

    @Builder.Default
    private Boolean status = true;

    private String message;

    private Object data;

    @Builder.Default
    private Instant timestamp = Instant.now();

    public ResponseEntity<Map<String, Object>> create()
    {
        Map<String, Object> response = new HashMap<>();

        response.put("status", status);
        if(message!=null)
            response.put("message", message);
        if (data != null) {
            response.put("data", data);
        }
        response.put("timestamp", timestamp);

        return ResponseEntity.status(httpStatus).body(response);
    }
}
