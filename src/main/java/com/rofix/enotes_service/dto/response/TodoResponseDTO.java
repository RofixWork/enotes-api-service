package com.rofix.enotes_service.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TodoResponseDTO {
    private Long id;
    private String title;
    private String description;
    private TodoStatusDTO status;
    private Integer createdBy;
    private Instant createdOn;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class TodoStatusDTO {
        private Integer id;
        private String name;
    }
}
