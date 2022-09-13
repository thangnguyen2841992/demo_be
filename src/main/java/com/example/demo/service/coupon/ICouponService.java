package com.example.demo.service.coupon;

import com.example.demo.model.entity.category.Category;
import com.example.demo.model.entity.merchant.Merchant;
import com.example.demo.model.entity.order.Coupon;

import java.util.Optional;

public interface ICouponService {
    Iterable<Coupon> findAll();

    Optional<Coupon> findById(Long id);

    Coupon save(Coupon coupon);

    void deleteById(Long id);

    Iterable<Coupon> findCouponsByMerchant(Merchant merchant);

}
