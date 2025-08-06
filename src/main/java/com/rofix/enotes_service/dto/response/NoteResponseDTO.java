package com.rofix.enotes_service.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rofix.enotes_service.entity.Category;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NoteResponseDTO {
    private Long id;
    private String title;
    private String description;
    private CategoryDTO category;
    private Integer createdBy;
    private Integer updatedBy;
    private Instant createdOn;
    private Instant updatedOn;
    
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CategoryDTO {
        private Long id;
        private String name;
    }

}
