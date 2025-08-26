package com.rofix.enotes_service.endpoint;

import static com.rofix.enotes_service.config.AppConstants.ROLE_ADMIN;
import static com.rofix.enotes_service.config.AppConstants.ROLE_ADMIN_USER;
import com.rofix.enotes_service.dto.request.CategoryRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Category", description = "All Category Endpoint APIs")
@RequestMapping(value = "/api/v1/categories", produces = MediaType.APPLICATION_JSON_VALUE)
public interface CategoryEndpoint {

    @Operation(summary = "Create Category Endpoint", tags = {"Category"}, description = "Admin Create new Category")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> createCategory(@Valid @RequestBody CategoryRequestDTO category);

    @Operation(summary = "Get All Categories Endpoint", tags = {"Category"}, description = "Admin get All Categories")
    @GetMapping
    @PreAuthorize(ROLE_ADMIN)
    ResponseEntity<?> getAllCategories();

    @Operation(summary = "Get Active Categories Endpoint", tags = {"Category"}, description = "Admin, User get only Active Categories")
    @GetMapping("/active")
    @PreAuthorize(ROLE_ADMIN_USER)
    ResponseEntity<?> getActiveCategories();

    @Operation(summary = "Get Category by ID Endpoint", tags = {"Category"}, description = "Admin get Category Details")
    @GetMapping("/{id}")
    @PreAuthorize(ROLE_ADMIN)
    ResponseEntity<?> getCategory(@Min(value = 1) @PathVariable Long id);

    @Operation(summary = "Update Exist Category Endpoint", tags = {"Category"}, description = "Admin update exist Category")
    @PutMapping("/{id}")
    @PreAuthorize(ROLE_ADMIN)
    ResponseEntity<?> updateCategory(@Min(value = 1) @PathVariable Long id, @Valid @RequestBody CategoryRequestDTO categoryDTO);

    @Operation(summary = "Delete Category Endpoint", tags = {"Category"}, description = "Admin Delete category by ID.")
    @DeleteMapping("/{id}")
    @PreAuthorize(ROLE_ADMIN)
    ResponseEntity<?> deleteCategory(@Min(value = 1) @PathVariable Long id);
}
