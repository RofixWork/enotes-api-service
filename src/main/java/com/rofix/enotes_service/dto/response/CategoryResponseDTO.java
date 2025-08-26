package com.rofix.enotes_service.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

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
    private Boolean isActive;
}
