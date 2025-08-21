package com.rofix.enotes_service.helper;

import com.rofix.enotes_service.dto.request.CategoryRequestDTO;
import com.rofix.enotes_service.dto.response.CategoryResponseDTO;
import com.rofix.enotes_service.entity.Category;
import com.rofix.enotes_service.exception.base.ConflictException;
import com.rofix.enotes_service.exception.base.NotFoundException;
import com.rofix.enotes_service.repository.CategoryRepository;
import com.rofix.enotes_service.utils.LoggerUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.event.Level;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CategoryHelper {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public List<CategoryResponseDTO> getCategoryResponseDTO(List<Category> categoryList)
    {
        return categoryList.stream()
                .map(category -> modelMapper.map(category, CategoryResponseDTO.class))
                .toList();
    }

    public Category getByIdAndActiveAndNotDeletedOrThrow(Long id)
    {
        return categoryRepository.findByIdAndIsDeletedIsFalseAndIsActiveIsTrue(id).orElseThrow(() -> {
            LoggerUtils.createLog(Level.ERROR, CategoryHelper.class.getName(), "getByIdAndActiveAndNotDeletedOrThrow", "category with Id [{}] not found!!!", id);
            return new NotFoundException("category with Id " + id + " not found" );
        });
    }

    public Category getCategoryByIdThrow(Long id)
    {
        return categoryRepository.findById(id).orElseThrow(() -> {
            LoggerUtils.createLog(Level.ERROR, CategoryHelper.class.getName(), "getCategoryByIdThrow", "category with Id [{}] not found!!!", id);
            return new NotFoundException("category with Id " + id + " not found" );
        });
    }

    public Category getUpdatedCategory(Category category, CategoryRequestDTO categoryDTO)
    {
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        category.setIsActive(categoryDTO.getIsActive());
        category.setIsDeleted(categoryDTO.getIsDeleted());

        return category;
    }

    public void checkedCategory(String name, Long id)
    {
        boolean categoryExist = id == null ?
                categoryRepository.existsByNameIgnoreCase(name)
                : categoryRepository.existsByNameIgnoreCaseAndIdNot(name, id);

        if(categoryExist)
        {
            LoggerUtils.createLog(Level.ERROR, CategoryHelper.class.getName(), "checkedCategory", "Category with name [{}] already exists!!!", name);
            throw new ConflictException("Category with name '" + name + "' already exists!");
        }
    }
}
