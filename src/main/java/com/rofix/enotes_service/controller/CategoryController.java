package com.rofix.enotes_service.controller;

import com.rofix.enotes_service.dto.request.CategoryRequestDTO;
import com.rofix.enotes_service.dto.response.CategoryResponseDTO;
import com.rofix.enotes_service.utils.ResponseUtils;
import com.rofix.enotes_service.service.CategoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/categories", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryRequestDTO category){
        CategoryResponseDTO savedCategory = categoryService.saveCategory(category);
        return ResponseUtils.createSuccessResponse(HttpStatus.CREATED, "Category created successfully", savedCategory);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllCategories(){
        List<CategoryResponseDTO> categories =  categoryService.getAllCategories();
        return ResponseUtils.createSuccessResponse("Get All Categories", categories);
    }

    @GetMapping("/active")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<?> getActiveCategories(){
        List<CategoryResponseDTO> activeCategories =  categoryService.getActiveCategories();
        return ResponseUtils.createSuccessResponse("Get Active Categories", activeCategories);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getCategory(@Min(value = 1) @PathVariable Long id) {
        CategoryResponseDTO category = categoryService.getCategoryById(id);
        return ResponseUtils.createSuccessResponse("Get Category", category);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateCategory(@Min(value = 1) @PathVariable Long id, @Valid @RequestBody CategoryRequestDTO categoryDTO){
        CategoryResponseDTO updated = categoryService.editCategory(id, categoryDTO);

        return ResponseUtils.createSuccessResponse("Category has been Updated", updated);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteCategory(@Min(value = 1) @PathVariable Long id) {
        String status = categoryService.deleteCategoryById(id);

        return ResponseUtils.createSuccessResponse(status);
    }
}
