package com.example.demo.service.merchant;

import com.example.demo.model.entity.merchant.Merchant;
import com.example.demo.repository.IMerchantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MerchantService implements IMerchantService{
    @Autowired
    private IMerchantRepository merchantRepository;
    @Override
    public Iterable<Merchant> findAll() {
        return merchantRepository.findAll();
    }

    @Override
    public Optional<Merchant> findById(Long id) {
        return merchantRepository.findById(id);
    }

    @Override
    public Merchant save(Merchant merchant) {
        return merchantRepository.save(merchant);
    }

    @Override
    public void deleteById(Long id) {
        merchantRepository.deleteById(id);
    }

    @Override
    public Optional<Merchant> findMerchantByUser_Id(Long userId) {
        return merchantRepository.findMerchantByUserId(userId);
    }
}
