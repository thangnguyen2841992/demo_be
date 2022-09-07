package com.example.demo.controller;

import com.example.demo.model.dto.ErrorMessage;
import com.example.demo.model.entity.dish.Dish;
import com.example.demo.model.entity.merchant.Merchant;
import com.example.demo.model.entity.auth.User;
import com.example.demo.service.dish.IDishService;
import com.example.demo.service.merchant.IMerchantService;
import com.example.demo.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/merchants")
public class MerchantRestController {

    @Autowired
    private IMerchantService merchantService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IDishService dishService;


    @GetMapping
    public ResponseEntity<Iterable<Merchant>> findAllMerchant() {
        Iterable<Merchant> merchants = merchantService.findAll();
        return new ResponseEntity<>(merchants, HttpStatus.OK);
    }

    @GetMapping("/merchant/user/{userId}")
    public ResponseEntity<?> getCurrentUserMerchant(@PathVariable Long userId) {
        Optional<User> userOptional = this.userService.findById(userId);
        User currentUser = userOptional.get();
        Optional<Merchant> findMerchant = merchantService.findMerchantByUser_Id(currentUser.getId());
        if (!findMerchant.isPresent()) {
            ErrorMessage errorMessage = new ErrorMessage("Tài khoản này không phải là chủ cửa hàng");
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(findMerchant.get(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Merchant> updateMerchant(@PathVariable Long id, @RequestBody Merchant newMerchant) {
        Optional<Merchant> merchantOptional = merchantService.findById(id);
        if (!merchantOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        newMerchant.setId(id);
        return new ResponseEntity<>(merchantService.save(newMerchant), HttpStatus.OK);
    }
    @GetMapping("/user/{userId}/merchant/dishes")
    public ResponseEntity<?> findMerchantByUserId(@PathVariable Long userId) {
        Optional<Merchant> merchantOptional = merchantService.findMerchantByUser_Id(userId);
        if (!merchantOptional.isPresent()) {
            ErrorMessage errorMessage = new ErrorMessage("Cửa hàng không tồn tại");
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
        Iterable<Dish> dishes = dishService.findAllByMerchant(merchantOptional.get());
        return new ResponseEntity<>(dishes, HttpStatus.OK);
    }
}
