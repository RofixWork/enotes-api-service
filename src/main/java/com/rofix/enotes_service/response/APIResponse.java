package com.rofix.enotes_service.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class APIResponse<T> {
    private String message;

    @Builder.Default
    private T data = null;

    @Builder.Default
    private Boolean status = true;

    @Builder.Default
    private Instant timestamp = Instant.now();
}
