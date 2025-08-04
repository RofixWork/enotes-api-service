package com.rofix.enotes_service.service;

import com.rofix.enotes_service.dto.request.CategoryRequestDTO;
import com.rofix.enotes_service.dto.response.CategoryResponseDTO;
import com.rofix.enotes_service.entity.Category;
import jakarta.validation.Valid;

import java.util.List;

public interface CategoryService {
    CategoryResponseDTO saveCategory(CategoryRequestDTO category);
    List<CategoryResponseDTO> getAllCategories();

    List<CategoryResponseDTO> getActiveCategories();
}
