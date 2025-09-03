package com.rofix.enotes_service.service;

import com.rofix.enotes_service.dto.request.CategoryRequestDTO;
import com.rofix.enotes_service.dto.response.CategoryResponseDTO;
import com.rofix.enotes_service.entity.Category;
import com.rofix.enotes_service.helper.CategoryHelper;
import com.rofix.enotes_service.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
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
    @CacheEvict(value = {"getAllCategories", "getActiveCategories"}, allEntries = true)
    public CategoryResponseDTO saveCategory(CategoryRequestDTO categoryDTO) {

        categoryHelper.checkedCategory(categoryDTO.getName(), null);

        Category newCategory = modelMapper.map(categoryDTO, Category.class);
        Category savedCategory = categoryRepository.save(newCategory);
        log.info("[CategoryServiceImpl] :: [saveCategory] :: Category has been Saved Successfully...");

        return modelMapper.map(savedCategory, CategoryResponseDTO.class);
    }

    @Override
    @Cacheable("getAllCategories")
    public List<CategoryResponseDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        log.info("[CategoryServiceImpl] :: [getAllCategories] :: Get All Categories...");
        return categoryHelper.getCategoryResponseDTO(categories);
    }

    @Override
    @Cacheable("getActiveCategories")
    public List<CategoryResponseDTO> getActiveCategories() {
        List<Category> activeCategories = categoryRepository.findByIsActiveTrueAndIsDeletedFalse();
        log.info("[CategoryServiceImpl] :: [getActiveCategories] :: Get All Active Categories...");
        return categoryHelper.getCategoryResponseDTO(activeCategories);
    }

    @Override
    @Cacheable(value = "getCategoryById", key = "#id")
    public CategoryResponseDTO getCategoryById(Long id) {
        Category category = categoryHelper.getByIdAndActiveAndNotDeletedOrThrow(id);
        log.info("[CategoryServiceImpl] :: [getCategoryById] :: Get Category with Id: [{}]", id);
        return modelMapper.map(category, CategoryResponseDTO.class);
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(value = {"getAllCategories", "getActiveCategories"}, allEntries = true),
                    @CacheEvict(value = "getCategoryById", key = "#id")
            }
    )
    public String deleteCategoryById(Long id) {
        Category category = categoryHelper.getByIdAndActiveAndNotDeletedOrThrow(id);

        category.setIsDeleted(true);
        categoryRepository.save(category);

        log.info("Category with id [{}] and name [{}] has been deleted successfully...", id, category.getName());
        return "Category with name '" + category.getName() + "' has been deleted successfully...";
    }

    @Override
    @Caching(
            evict = {@CacheEvict(value = {"getAllCategories", "getActiveCategories"}, allEntries = true)},
            put = {@CachePut(value = "getCategoryById", key = "#id")}
    )
    public CategoryResponseDTO editCategory(Long id, CategoryRequestDTO categoryDTO) {
        Category category = categoryHelper.getCategoryByIdThrow(id);

        categoryHelper.checkedCategory(categoryDTO.getName(), id);

        category = categoryHelper.getUpdatedCategory(category, categoryDTO);
        category = categoryRepository.save(category);

        log.info("[CategoryServiceImpl] :: [getCategoryById] :: Category with Id [{}] has been updated successfully...", id);
        return modelMapper.map(category, CategoryResponseDTO.class);
    }

}
