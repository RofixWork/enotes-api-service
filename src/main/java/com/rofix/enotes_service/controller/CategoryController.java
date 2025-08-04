package com.rofix.enotes_service.controller;

import com.rofix.enotes_service.dto.request.CategoryRequestDTO;
import com.rofix.enotes_service.dto.response.CategoryResponseDTO;
import com.rofix.enotes_service.entity.Category;
import com.rofix.enotes_service.response.APIResponse;
import com.rofix.enotes_service.service.CategoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<APIResponse<CategoryResponseDTO>> createCategory(@Valid @RequestBody CategoryRequestDTO category){
        CategoryResponseDTO savedCategory = categoryService.saveCategory(category);
        APIResponse<CategoryResponseDTO> response = APIResponse.<CategoryResponseDTO>builder()
                .message("Category created successfully")
                .data(savedCategory)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<APIResponse<List<CategoryResponseDTO>>> getAllCategories(){
        List<CategoryResponseDTO> categories =  categoryService.getAllCategories();

        APIResponse<List<CategoryResponseDTO>> response = APIResponse.<List<CategoryResponseDTO>>builder()
                .message("Get All Categories")
                .data(categories)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/active")
    public ResponseEntity<APIResponse<List<CategoryResponseDTO>>> getActiveCategories(){
        List<CategoryResponseDTO> categories =  categoryService.getActiveCategories();

        APIResponse<List<CategoryResponseDTO>> response = APIResponse.<List<CategoryResponseDTO>>builder()
                .message("Get Active Categories")
                .data(categories)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<APIResponse<CategoryResponseDTO>> getCategory(@Min(value = 1) @PathVariable Long id) {
        CategoryResponseDTO category = categoryService.getCategoryById(id);

        APIResponse<CategoryResponseDTO> categoryResponseDTO = APIResponse.<CategoryResponseDTO>builder()
                .message("Get Category")
                .data(category)
                .build();

        return ResponseEntity.ok(categoryResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryRequestDTO categoryDTO){
        CategoryResponseDTO updated = categoryService.editCategory(id, categoryDTO);

        APIResponse<CategoryResponseDTO> categoryResponseDTO = APIResponse.<CategoryResponseDTO>builder()
                .message("Category has been Updated")
                .data(updated)
                .build();

        return ResponseEntity.ok(categoryResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<APIResponse<Void>> deleteCategory(@Min(value = 1) @PathVariable Long id) {
        String status = categoryService.deleteCategoryById(id);

        APIResponse<Void> categoryResponseDTO = APIResponse.<Void>builder()
                .message(status)
                .build();

        return ResponseEntity.ok(categoryResponseDTO);
    }
}
