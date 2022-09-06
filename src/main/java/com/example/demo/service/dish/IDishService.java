package com.example.demo.service.dish;

import com.example.demo.model.entity.Merchant;
import com.example.demo.model.entity.category.Category;
import com.example.demo.model.entity.dish.Dish;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
}
