package com.example.demo.service.cartDetail;

import com.example.demo.model.entity.cart.Cart;
import com.example.demo.model.entity.cart.CartDetail;
import com.example.demo.model.entity.dish.Dish;
import com.example.demo.repository.ICartDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartDetailService implements ICartDetailService {
    @Autowired
    private ICartDetailRepository cartDetailRepository;

    @Override
    public Iterable<CartDetail> findAll() {
        return this.cartDetailRepository.findAll();
    }

    @Override
    public Optional<CartDetail> findById(Long id) {
        return this.cartDetailRepository.findById(id);
    }

    @Override
    public CartDetail save(CartDetail cartDetail) {
        return this.cartDetailRepository.save(cartDetail);
    }

    @Override
    public void deleteById(Long id) {
        this.cartDetailRepository.deleteById(id);
    }

    @Override
    public Iterable<CartDetail> findAllByCart(Cart cart) {
        return this.cartDetailRepository.findAllByCart(cart);
    }

    @Override
    public Optional<CartDetail> findByCartAndDish(Cart cart, Dish dish) {
        return this.cartDetailRepository.findByCartAndDish(cart, dish);
    }

    @Override
    public void deleteAllByCart(Cart cart) {
        this.cartDetailRepository.deleteAllByCart(cart);
    }
}
