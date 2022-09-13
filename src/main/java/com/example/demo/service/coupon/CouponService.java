package com.example.demo.service.coupon;

import com.example.demo.model.entity.merchant.Merchant;
import com.example.demo.model.entity.order.Coupon;
import com.example.demo.repository.ICouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CouponService implements ICouponService {
    @Autowired
    private ICouponRepository couponRepository;
    @Override
    public Iterable<Coupon> findAll() {
        return this.couponRepository.findAll();
    }

    @Override
    public Optional<Coupon> findById(Long id) {
        return this.couponRepository.findById(id);
    }

    @Override
    public Coupon save(Coupon coupon) {
        return this.couponRepository.save(coupon);
    }

    @Override
    public void deleteById(Long id) {
            this.couponRepository.deleteById(id);
    }

    @Override
    public Iterable<Coupon> findCouponsByMerchant(Merchant merchant) {
        return this.couponRepository.findCouponsByMerchant(merchant);
    }
}
