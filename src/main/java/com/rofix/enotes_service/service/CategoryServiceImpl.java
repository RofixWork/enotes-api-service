package com.rofix.enotes_service.service;

import com.rofix.enotes_service.dto.request.CategoryRequestDTO;
import com.rofix.enotes_service.dto.response.CategoryResponseDTO;
import com.rofix.enotes_service.entity.Category;
import com.rofix.enotes_service.helper.CategoryHelper;
import com.rofix.enotes_service.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final CategoryHelper categoryHelper;

    @Override
    public CategoryResponseDTO saveCategory(CategoryRequestDTO categoryDTO) {

       categoryHelper.checkedCategory(categoryDTO.getName(), null);

        Category newCategory = modelMapper.map(categoryDTO, Category.class);
        Category savedCategory = categoryRepository.save(newCategory);
        log.info("[CategoryServiceImpl] :: [saveCategory] :: Category has been Saved Successfully...");

        return modelMapper.map(savedCategory,CategoryResponseDTO.class);
    }

    @Override
    public List<CategoryResponseDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        log.info("[CategoryServiceImpl] :: [getAllCategories] :: Get All Categories...");
        return categoryHelper.getCategoryResponseDTO(categories);
    }

    @Override
    public List<CategoryResponseDTO> getActiveCategories() {
        List<Category> activeCategories =  categoryRepository.findByIsActiveTrueAndIsDeletedFalse();
        log.info("[CategoryServiceImpl] :: [getActiveCategories] :: Get All Active Categories...");
        return categoryHelper.getCategoryResponseDTO(activeCategories);
    }

    @Override
    public CategoryResponseDTO getCategoryById(Long id) {
        log.info("[CategoryServiceImpl] :: [getCategoryById] :: Get Category with Id: [{}]", id);
        return modelMapper.map(categoryHelper.getByIdAndActiveAndNotDeletedOrThrow(id), CategoryResponseDTO.class);
    }

    @Override
    public String deleteCategoryById(Long id) {
        Category category = categoryHelper.getByIdAndActiveAndNotDeletedOrThrow(id);

        category.setIsDeleted(true);
        categoryRepository.save(category);

        log.info("[CategoryServiceImpl] :: [getCategoryById] :: Category with name [{}] has been deleted successfully...", id);
        return "Category with name '" + category.getName() + "' has been deleted successfully...";
    }

    @Override
    public CategoryResponseDTO editCategory(Long id, CategoryRequestDTO categoryDTO) {
        Category category = categoryHelper.getCategoryByIdThrow(id);

        categoryHelper.checkedCategory(categoryDTO.getName(), id);

        category = categoryHelper.getUpdatedCategory(category, categoryDTO);
        category = categoryRepository.save(category);

        log.info("[CategoryServiceImpl] :: [getCategoryById] :: Category with Id [{}] has been updated successfully...", id);
        return modelMapper.map(category,CategoryResponseDTO.class);
    }

}
