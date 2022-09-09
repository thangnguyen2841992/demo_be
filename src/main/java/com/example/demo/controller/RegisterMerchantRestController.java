package com.example.demo.controller;

import com.example.demo.model.dto.ErrorMessage;
import com.example.demo.model.dto.merchant.MerchantRegisterForm;
import com.example.demo.model.entity.auth.Role;
import com.example.demo.model.entity.auth.User;
import com.example.demo.model.entity.merchant.Merchant;
import com.example.demo.model.entity.merchant.MerchantRegisterRequest;
import com.example.demo.service.merchant.IMerchantRegisterService;
import com.example.demo.service.merchant.IMerchantService;
import com.example.demo.service.role.IRoleService;
import com.example.demo.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/registerMerchant")
public class RegisterMerchantRestController {

    @Autowired
    private IMerchantService merchantService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IMerchantRegisterService merchantRegisterService;
    @Autowired
    private IRoleService roleService;
    @PostMapping
    public ResponseEntity<?> registerMerchant(@RequestBody MerchantRegisterForm merchantRegisterForm) {
//

        User user = merchantRegisterForm.getUser();


        Optional<MerchantRegisterRequest> merchantRegisterRequest = merchantRegisterService.findByUserAndReviewed(user, false);
        if (merchantRegisterRequest.isPresent()) {
            ErrorMessage errorMessage = new ErrorMessage();
            errorMessage.setMessage("Không thể tạo thêm yêu cầu: đã tồn tại yêu cầu đang chờ xét duyệt");
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
        MerchantRegisterRequest merchant = new MerchantRegisterRequest();
        merchant.setUser(merchantRegisterForm.getUser());
        merchant.setName(merchantRegisterForm.getName());
        merchant.setDescription(merchantRegisterForm.getDescription());
        merchant.setAddress(merchantRegisterForm.getAddress());
        merchant.setPhone(merchantRegisterForm.getPhone());
        merchant.setOpenTime(merchantRegisterForm.getOpenTime());
        merchant.setCloseTime(merchantRegisterForm.getCloseTime());
        merchant = merchantRegisterService.save(merchant);
        return new ResponseEntity<>(merchant, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> findAllMerchantRegisterRequest() {
        Iterable<MerchantRegisterRequest> merchantRegisterRequest = merchantRegisterService.findMerchantByReviewed(false);
        return new ResponseEntity<>(merchantRegisterRequest, HttpStatus.OK);
    }
    @PostMapping("/accept/{id}")
    public ResponseEntity<?> acceptRegisterRequest(@PathVariable Long id) {
        Optional<MerchantRegisterRequest> findMerchantRegisterRequest = merchantRegisterService.findById(id);
        if (!findMerchantRegisterRequest.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        MerchantRegisterRequest mrr = findMerchantRegisterRequest.get();
        // tao dt merchant moi va luu db
        Merchant merchant = new Merchant();
        merchant.setUser(mrr.getUser());
        merchant.setName(mrr.getName());
        merchant.setDescription(mrr.getDescription());
        merchant.setAddress(mrr.getAddress());
        merchant.setPhone(mrr.getPhone());
        merchant.setOpenTime(mrr.getOpenTime());
        merchant.setCloseTime(mrr.getCloseTime());
        merchant.setAvatar("unnamed.png");
        merchant.setImageBanner("unnamed.jpg");
        // sua role user thanh role merchant
        User user = mrr.getUser();
        Optional<Role> roleOptional = this.roleService.findByName("ROLE_MERCHANT");
        if (!roleOptional.isPresent()) {
            return new ResponseEntity<>(new ErrorMessage("Role không tồn tại"), HttpStatus.BAD_REQUEST);
        }
        List<Role> roles = new ArrayList<>();
        roles.add(new Role(roleOptional.get().getId(), roleOptional.get().getName()));
        user.setRoles(roles);
        // thay doi merchanRegisterRequest ==> reviewed=true, accepted = true
        mrr.setReviewed(true);
        mrr.setAccept(true);

        //luu thay doi vao DB
        merchantService.save(merchant);
        userService.save(user);
        merchantRegisterService.save(mrr);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
