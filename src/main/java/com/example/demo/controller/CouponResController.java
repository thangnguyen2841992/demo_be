package com.example.demo.controller;

import com.example.demo.model.dto.ErrorMessage;
import com.example.demo.model.entity.merchant.Merchant;
import com.example.demo.model.entity.order.Coupon;
import com.example.demo.service.coupon.ICouponService;
import com.example.demo.service.merchant.IMerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/coupons")
@CrossOrigin("*")
public class CouponResController {
    @Autowired
    private ICouponService couponService;
    @Autowired
    private IMerchantService merchantService;

    @PostMapping("/create-coupon")
    public ResponseEntity<?> createNewCoupon(@RequestBody Coupon coupon) {
        Coupon newCoupon = new Coupon();
        newCoupon.setName(coupon.getName());
        newCoupon.setType("Cửa hàng");
        newCoupon.setValue(coupon.getValue());
        newCoupon.setMerchant(coupon.getMerchant());
        newCoupon.setDescription(coupon.getDescription());
        this.couponService.save(newCoupon);
        return new ResponseEntity<>(newCoupon, HttpStatus.CREATED);
    }
    @GetMapping("/merchant/{merchantId}")
    public ResponseEntity<?> getAllCouponOfMerchant(@PathVariable Long merchantId) {
        Optional<Merchant> merchantOptional = this.merchantService.findById(merchantId);
        if (!merchantOptional.isPresent()) {
            return new ResponseEntity<>(new ErrorMessage("Cửa hàng không tồn tại!"), HttpStatus.BAD_REQUEST);
        }
        Iterable<Coupon> coupons = this.couponService.findCouponsByMerchant(merchantOptional.get());
        return new ResponseEntity<>(coupons, HttpStatus.OK);
    }
}
