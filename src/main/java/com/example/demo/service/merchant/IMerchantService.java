package com.example.demo.service.merchant;

import com.example.demo.model.entity.merchant.Merchant;

import java.util.Optional;

public interface IMerchantService {
    Iterable<Merchant> findAll();

    Optional<Merchant> findById(Long id);

    Merchant save(Merchant merchant);

    void deleteById(Long id);

    Optional<Merchant> findMerchantByUser_Id(Long userId);

}
