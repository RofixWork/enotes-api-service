package com.rofix.enotes_service.helper;

import com.rofix.enotes_service.dto.response.CategoryResponseDTO;
import com.rofix.enotes_service.entity.Category;
import com.rofix.enotes_service.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

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
        return categoryRepository.findByIdAndIsDeletedIsFalseAndIsActiveIsTrue(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "category with Id " + id + " not found" ));
    }
}
