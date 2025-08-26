package com.rofix.enotes_service.controller;

import com.rofix.enotes_service.dto.request.CategoryRequestDTO;
import com.rofix.enotes_service.dto.response.CategoryResponseDTO;
import com.rofix.enotes_service.endpoint.CategoryEndpoint;
import com.rofix.enotes_service.utils.ResponseUtils;
import com.rofix.enotes_service.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
public class CategoryController implements CategoryEndpoint {
    private final CategoryService categoryService;

    @Override
    public ResponseEntity<?> createCategory(CategoryRequestDTO category){
        CategoryResponseDTO savedCategory = categoryService.saveCategory(category);
        return ResponseUtils.createSuccessResponse(HttpStatus.CREATED, "Category created successfully", savedCategory);
    }

    @Override
    public ResponseEntity<?> getAllCategories(){
        List<CategoryResponseDTO> categories =  categoryService.getAllCategories();
        return ResponseUtils.createSuccessResponse("Get All Categories", categories);
    }

    @Override
    public ResponseEntity<?> getActiveCategories(){
        List<CategoryResponseDTO> activeCategories =  categoryService.getActiveCategories();
        return ResponseUtils.createSuccessResponse("Get Active Categories", activeCategories);
    }

    @Override
    public ResponseEntity<?> getCategory(Long id) {
        CategoryResponseDTO category = categoryService.getCategoryById(id);
        return ResponseUtils.createSuccessResponse("Get Category", category);
    }

    @Override
    public ResponseEntity<?> updateCategory(Long id, CategoryRequestDTO categoryDTO){
        CategoryResponseDTO updated = categoryService.editCategory(id, categoryDTO);

        return ResponseUtils.createSuccessResponse("Category has been Updated", updated);
    }

    @Override
    public ResponseEntity<?> deleteCategory(Long id) {
        String status = categoryService.deleteCategoryById(id);

        return ResponseUtils.createSuccessResponse(status);
    }
}
