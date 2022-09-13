package com.example.demo.service.cartDetail;

import com.example.demo.model.entity.cart.Cart;
import com.example.demo.model.entity.cart.CartDetail;
import com.example.demo.model.entity.dish.Dish;

import java.util.Optional;

public interface ICartDetailService {
    Iterable<CartDetail> findAll();

    Optional<CartDetail> findById(Long id);

    CartDetail save(CartDetail cartDetail);

    void deleteById(Long id);
    Iterable<CartDetail> findAllByCart(Cart cart);

    Optional<CartDetail> findByCartAndDish(Cart cart, Dish dish);

    void deleteAllByCart(Cart cart);
}
