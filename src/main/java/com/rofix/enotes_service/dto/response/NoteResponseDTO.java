package com.rofix.enotes_service.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

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
    private FileDTO file;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CategoryDTO {
        private Long id;
        private String name;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FileDTO {
        @JsonProperty("displayName")
        private String displayFileName;

        @JsonProperty("originalName")
        private String originalFileName;
    }

}
