package com.rofix.enotes_service.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NoteRequestDTO {
    @NotBlank(message = "The title cannot be empty or null.")
    @Size(min = 6, message = "The title must be at least 6 characters long.")
    private String title;

//    @NotBlank(message = "The description cannot be empty or null.")
    @Size(min = 10, message = "The description must be at least 10 characters long.")
    private String description;

    @NotNull(message = "The category ID is required.")
    @Min(value = 1, message = "The category ID must be a positive number.")
    private Long categoryId;
}
