package com.example.demo.repository;

import com.example.demo.model.entity.cart.Cart;
import com.example.demo.model.entity.cart.CartDetail;
import com.example.demo.model.entity.dish.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface ICartDetailRepository extends JpaRepository<CartDetail, Long> {
    Iterable<CartDetail> findAllByCart(Cart cart);

    void deleteAllByCart(Cart cart);

    Optional<CartDetail> findByCartAndDish(Cart cart, Dish dish);
}
