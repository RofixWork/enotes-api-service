package com.rofix.enotes_service.endpoint;

import static com.rofix.enotes_service.config.AppConstants.ROLE_ADMIN;
import static com.rofix.enotes_service.config.AppConstants.ROLE_ADMIN_USER;
import com.rofix.enotes_service.dto.request.CategoryRequestDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/api/v1/categories", produces = MediaType.APPLICATION_JSON_VALUE)
public interface CategoryEndpoint {
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> createCategory(@Valid @RequestBody CategoryRequestDTO category);

    @GetMapping
    @PreAuthorize(ROLE_ADMIN)
    ResponseEntity<?> getAllCategories();

    @GetMapping("/active")
    @PreAuthorize(ROLE_ADMIN_USER)
    ResponseEntity<?> getActiveCategories();

    @GetMapping("/{id}")
    @PreAuthorize(ROLE_ADMIN)
    ResponseEntity<?> getCategory(@Min(value = 1) @PathVariable Long id);

    @PutMapping("/{id}")
    @PreAuthorize(ROLE_ADMIN)
    ResponseEntity<?> updateCategory(@Min(value = 1) @PathVariable Long id, @Valid @RequestBody CategoryRequestDTO categoryDTO);

    @DeleteMapping("/{id}")
    @PreAuthorize(ROLE_ADMIN)
    ResponseEntity<?> deleteCategory(@Min(value = 1) @PathVariable Long id);
}
