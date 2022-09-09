package com.example.demo.service.dish;

import com.example.demo.model.dto.SearchForm;
import com.example.demo.model.entity.merchant.Merchant;
import com.example.demo.model.entity.category.Category;
import com.example.demo.model.entity.dish.Dish;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IDishService {
    Iterable<Dish> findAll();

    Optional<Dish> findById(Long id);

    Dish save(Dish dish);

    void deleteById(Long id);

    Page<Dish> findAll(Pageable pageable);

    Page<Dish> findAllByNameContaining(String name, Pageable pageable);

    int countDishByCategoriesIsContaining(Category category);

    Iterable<Dish> findMostPurchased(int top);

    Iterable<Dish> findAllByMerchant(Merchant merchant);

    Iterable<Dish> findAllByMerchant_Id(Long id);

    Iterable<Dish> findDishesWithSameCategoryWith(Long dishId, int limit);

    Iterable<Dish> findAllDishes(int limit);

    Iterable<Dish> searchByForm(SearchForm searchForm);

    Iterable<Dish> searchByNameOnly(String name, int limit);

    Iterable<Dish> searchByCategoriesOnly(List<Category> categories, int limit);

    Iterable<Dish> searchByNameAndCategories(String name, List<Category> categories, int limit);

    String generateCategoryIdListString(List<Category> categories);
}
