package com.example.demo.service.dish;

import com.example.demo.model.entity.Merchant;
import com.example.demo.model.entity.category.Category;
import com.example.demo.model.entity.dish.Dish;
import com.example.demo.repository.IDishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DishService implements IDishService {
    @Autowired
    private IDishRepository dishRepository;

    @Override
    public Iterable<Dish> findAll() {
        return dishRepository.findAll();
    }

    @Override
    public Optional<Dish> findById(Long id) {
        return dishRepository.findById(id);
    }

    @Override
    public Dish save(Dish dish) {
        return dishRepository.save(dish);
    }

    @Override
    public void deleteById(Long id) {
        dishRepository.deleteById(id);
    }

    @Override
    public Page<Dish> findAll(Pageable pageable) {
        return this.dishRepository.findAll(pageable);
    }

    @Override
    public Page<Dish> findAllByNameContaining(String name, Pageable pageable) {
        return this.dishRepository.findAllByNameContaining(name, pageable);
    }

    @Override
    public int countDishByCategoriesIsContaining(Category category) {
        return this.dishRepository.countDishByCategoriesIsContaining(category);
    }

    @Override
    public Iterable<Dish> findMostPurchased(int top) {
        return dishRepository.findTopPurchased(top);
    }

    @Override
    public Iterable<Dish> findAllByMerchant(Merchant merchant) {
        return this.dishRepository.findAllByMerchant(merchant);
    }

    @Override
    public Iterable<Dish> findAllByMerchant_Id(Long id) {
        return this.dishRepository.findAllByMerchant_Id(id);
    }

    @Override
    public Iterable<Dish> findDishesWithSameCategoryWith(Long dishId, int limit) {
        return this.dishRepository.findDishesWithSameCategoryWith(dishId, limit);
    }
}
