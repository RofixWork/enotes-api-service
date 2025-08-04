package com.rofix.enotes_service.service;

import com.rofix.enotes_service.dto.request.CategoryRequestDTO;
import com.rofix.enotes_service.dto.response.CategoryResponseDTO;
import com.rofix.enotes_service.entity.Category;
import com.rofix.enotes_service.repository.CategoryRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public CategoryResponseDTO saveCategory(CategoryRequestDTO category) {
        Category newCategory = modelMapper.map(category, Category.class),
                savedCategory = categoryRepository.save(newCategory);

        return modelMapper.map(savedCategory,CategoryResponseDTO.class);
    }

    @Override
    public List<CategoryResponseDTO> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(category -> modelMapper.map(category, CategoryResponseDTO.class))
                .toList();
    }

    @Override
    public List<CategoryResponseDTO> getActiveCategories() {
        return categoryRepository.findByIsActiveTrue().stream()
                .map(category -> modelMapper.map(category, CategoryResponseDTO.class))
                .toList();
    }
}
