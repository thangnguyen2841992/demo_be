package com.example.demo.service.merchant;

import com.example.demo.model.entity.auth.User;
import com.example.demo.model.entity.merchant.MerchantRegisterRequest;

import java.util.Optional;

public interface IMerchantRegisterService {
    Iterable<MerchantRegisterRequest> findAll();

    Optional<MerchantRegisterRequest> findById(Long id);

    MerchantRegisterRequest save(MerchantRegisterRequest merchantRegisterRequest);

    void deleteById(Long id);

    Optional<MerchantRegisterRequest> findByUserAndReviewed(User user, boolean reviewed);
    Iterable<MerchantRegisterRequest> findMerchantByReviewed(boolean reviewed);

}
