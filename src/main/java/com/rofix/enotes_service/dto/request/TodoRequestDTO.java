package com.rofix.enotes_service.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TodoRequestDTO {
    @NotBlank(message = "The title cannot be empty or null.")
    @Size(min = 6, max = 1000, message = "The title must be between 6 and 1000 characters long.")
    private String title;

    @Size(min = 10, message = "The description must be at least 10 characters long.")
    private String description;

    @NotNull(message = "The status is required.")
    @Min(value = 1, message = "The status must be a positive number.")
    private Integer status;
}
