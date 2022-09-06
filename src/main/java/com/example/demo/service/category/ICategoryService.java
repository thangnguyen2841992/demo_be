package com.example.demo.service.category;

import com.example.demo.model.dto.category.CategoryDTO;
import com.example.demo.model.entity.category.Category;

import java.util.List;
import java.util.Optional;

public interface ICategoryService {
    Iterable<Category> findAll();

    Optional<Category> findById(Long id);

    Category save(Category category);

    void deleteById(Long id);

    Iterable<CategoryDTO> getAllCategoryDTO();


    List<Category> findTop5Categories();
}
