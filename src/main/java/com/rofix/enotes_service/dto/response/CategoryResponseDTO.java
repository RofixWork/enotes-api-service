package com.rofix.enotes_service.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.Instant;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryResponseDTO {
    private Long id;
    private String name;
    private String description;
//    private Boolean isActive;
//    private Boolean isDeleted;
//    private Integer createdBy;
//    private Integer updatedBy;
//    private Instant createdOn;
//    private Instant updatedOn;
}
