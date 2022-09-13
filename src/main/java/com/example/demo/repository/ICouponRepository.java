package com.example.demo.repository;

import com.example.demo.model.entity.merchant.Merchant;
import com.example.demo.model.entity.order.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ICouponRepository extends JpaRepository<Coupon, Long> {

    Iterable<Coupon> findCouponsByMerchant(Merchant merchant);
}
