package com.rofix.enotes_service.controller;

import com.rofix.enotes_service.entity.Category;
import com.rofix.enotes_service.response.APIResponse;
import com.rofix.enotes_service.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<APIResponse<Category>> createCategory(@RequestBody Category category){
        Category savedCategory = categoryService.saveCategory(category);
        APIResponse<Category> response = APIResponse.<Category>builder().message("Category created successfully").data(savedCategory).build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<APIResponse<List<Category>>> getAllCategories(){
        List<Category> categories =  categoryService.getAllCategories();

        APIResponse<List<Category>> response = APIResponse.<List<Category>>builder()
                .message("Get All Categories")
                .data(categories)
                .build();

        return ResponseEntity.ok(response);
    }
}
