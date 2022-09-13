package com.example.demo.repository;

import com.example.demo.model.entity.auth.User;
import com.example.demo.model.entity.cart.Cart;
import com.example.demo.model.entity.merchant.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findCartByUserAndMerchant(User user, Merchant merchant);

    Iterable<Cart> findCartByUser(User user);

}
