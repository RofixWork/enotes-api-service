package com.rofix.enotes_service.service;

import com.rofix.enotes_service.entity.Category;

import java.util.List;

public interface CategoryService {
    Category saveCategory(Category category);
    List<Category> getAllCategories();
}
