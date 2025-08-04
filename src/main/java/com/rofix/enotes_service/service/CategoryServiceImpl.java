package com.rofix.enotes_service.service;

import com.rofix.enotes_service.dto.request.CategoryRequestDTO;
import com.rofix.enotes_service.dto.response.CategoryResponseDTO;
import com.rofix.enotes_service.entity.Category;
import com.rofix.enotes_service.helper.CategoryHelper;
import com.rofix.enotes_service.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final CategoryHelper categoryHelper;

    @Override
    public CategoryResponseDTO saveCategory(CategoryRequestDTO category) {
        Category newCategory = modelMapper.map(category, Category.class),
                savedCategory = categoryRepository.save(newCategory);

        return modelMapper.map(savedCategory,CategoryResponseDTO.class);
    }

    @Override
    public List<CategoryResponseDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categoryHelper.getCategoryResponseDTO(categories);
    }

    @Override
    public List<CategoryResponseDTO> getActiveCategories() {
        List<Category> activeCategories =  categoryRepository.findByIsActiveTrueAndIsDeletedFalse();
        return categoryHelper.getCategoryResponseDTO(activeCategories);
    }

    @Override
    public CategoryResponseDTO getCategoryById(Long id) {
        return modelMapper.map(categoryHelper.getByIdAndActiveAndNotDeletedOrThrow(id), CategoryResponseDTO.class);
    }

    @Override
    public String deleteCategoryById(Long id) {
        Category category = categoryHelper.getByIdAndActiveAndNotDeletedOrThrow(id);

        category.setIsDeleted(true);
        categoryRepository.save(category);

        return "Category with name '" + category.getName() + "' has been deleted successfully...";
    }
}
