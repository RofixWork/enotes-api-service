package com.rofix.enotes_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequestDTO {

    @NotBlank(message = "The category name cannot be blank.")
    @Size(min = 3, max = 50, message = "The category name must be between 3 and 50 characters long.")
    private String name;

    @NotBlank(message = "The category description cannot be blank.")
    @Size(min = 6, max = 1000, message = "The category description must be between 6 and 1000 characters long.")
    private String description;

    @NotNull(message = "Category Active status cannot be null!!!")
    private Boolean isActive = true;

    private Boolean isDeleted = false;
}
