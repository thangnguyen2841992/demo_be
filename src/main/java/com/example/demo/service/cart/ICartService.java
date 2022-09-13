package com.example.demo.service.cart;

import com.example.demo.model.dto.cart.CartDetailDto;
import com.example.demo.model.dto.cart.CartDto;
import com.example.demo.model.entity.auth.User;
import com.example.demo.model.entity.cart.Cart;
import com.example.demo.model.entity.category.Category;
import com.example.demo.model.entity.dish.Dish;
import com.example.demo.model.entity.merchant.Merchant;

import java.util.List;
import java.util.Optional;

public interface ICartService {
    Iterable<Cart> findAll();

    Optional<Cart> findById(Long id);

    Cart save(Cart cart);

    void deleteById(Long id);

    Optional<Cart> findCartByUserAndMerchant(User user, Merchant merchant);

    Iterable<Cart> findAllCartByUser(User user);

    Cart createCartWithUserAndMerchant(User user, Merchant merchant);

    CartDto getCartDtoByUserAndMerchant(User user, Merchant merchant);

    List<CartDto> getAllCartDtoByUser(User user);

    CartDto addDishToCart(User user, CartDetailDto cartDetailDto);

    boolean changeDishQuantityInCart(Cart cart, Dish dish, int amount);

    void emptyCartById(Long cartId);
}
