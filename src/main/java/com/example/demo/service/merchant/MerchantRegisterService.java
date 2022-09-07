package com.example.demo.service.merchant;

import com.example.demo.model.entity.auth.User;
import com.example.demo.model.entity.merchant.MerchantRegisterRequest;
import com.example.demo.repository.IMerchantRegisterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MerchantRegisterService implements IMerchantRegisterService{
    @Autowired
    private IMerchantRegisterRepository merchantRegisterRepository;

    @Override
    public Iterable<MerchantRegisterRequest> findAll() {
        return this.merchantRegisterRepository.findAll();
    }

    @Override
    public Optional<MerchantRegisterRequest> findById(Long id) {
        return this.merchantRegisterRepository.findById(id);
    }

    @Override
    public MerchantRegisterRequest save(MerchantRegisterRequest merchantRegisterRequest) {
        return this.merchantRegisterRepository.save(merchantRegisterRequest);
    }

    @Override
    public void deleteById(Long id) {
        this.merchantRegisterRepository.deleteById(id);
    }

    @Override
    public Optional<MerchantRegisterRequest> findByUserAndReviewed(User user, boolean reviewed) {
        return this.merchantRegisterRepository.findByUserAndReviewed(user, reviewed);
    }

    @Override
    public Iterable<MerchantRegisterRequest> findMerchantByReviewed(boolean reviewed) {
        return this.merchantRegisterRepository.findMerchantByReviewed(reviewed);
    }
}
