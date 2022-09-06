package com.example.demo.service.merchant;

import com.example.demo.model.entity.Merchant;
import com.example.demo.model.entity.category.Category;

import java.util.Optional;

public interface IMerchantService {
    Iterable<Merchant> findAll();

    Optional<Merchant> findById(Long id);

    Merchant save(Merchant merchant);

    void deleteById(Long id);
}
