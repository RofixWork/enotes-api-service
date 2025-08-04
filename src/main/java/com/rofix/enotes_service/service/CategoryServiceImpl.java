package com.rofix.enotes_service.service;

import com.rofix.enotes_service.dto.request.CategoryRequestDTO;
import com.rofix.enotes_service.dto.response.CategoryResponseDTO;
import com.rofix.enotes_service.entity.Category;
import com.rofix.enotes_service.helper.CategoryHelper;
import com.rofix.enotes_service.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final CategoryHelper categoryHelper;

    @Override
    public CategoryResponseDTO saveCategory(CategoryRequestDTO categoryDTO) {

        if(categoryRepository.existsByNameIgnoreCase(categoryDTO.getName()))
        {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Category with name '" + categoryDTO.getName() + "' already exists!");
        }

        Category newCategory = modelMapper.map(categoryDTO, Category.class);
        newCategory.setCreatedBy(1);
        Category savedCategory = categoryRepository.save(newCategory);

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

    @Override
    public CategoryResponseDTO editCategory(Long id, CategoryRequestDTO categoryDTO) {
        Category category = categoryHelper.getCategoryByIdThrow(id);

        if(categoryRepository.existsByNameIgnoreCaseAndIdNot(categoryDTO.getName(), category.getId())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Category with name '" + categoryDTO.getName() + "' already exists!");
        }

        category = categoryHelper.getUpdatedCategory(category, categoryDTO);
        category = categoryRepository.save(category);

        return modelMapper.map(category,CategoryResponseDTO.class);
    }

}
