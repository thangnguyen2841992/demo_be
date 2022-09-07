package com.example.demo.controller;

import com.example.demo.model.dto.ErrorMessage;
import com.example.demo.model.dto.dish.DishForm;
import com.example.demo.model.entity.merchant.Merchant;
import com.example.demo.model.entity.dish.Dish;
import com.example.demo.service.dish.IDishService;
import com.example.demo.service.merchant.IMerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/dishes")
public class DishRestController {
    public final int ITEM_PER_PAGE = 9;

    @Autowired
    private IDishService dishService;

    @Value("C:/Users/Admin/IdeaProjects/Image")
    private String uploadPath;

    @Autowired
    private IMerchantService merchantService;

    @GetMapping("/page/{pageNumber}")
    public ResponseEntity<Page<Dish>> showDishes(@RequestParam(name = "q") Optional<String> q, @PathVariable int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, ITEM_PER_PAGE);
        Page<Dish> dishes = dishService.findAll(pageable);
        if (q.isPresent()) {
            dishes = dishService.findAllByNameContaining(q.get(), pageable);
        }
        return new ResponseEntity<>(dishes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Dish> findDishById(@PathVariable Long id) {
        Optional<Dish> dishOptional = dishService.findById(id);
        if(!dishOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(dishOptional.get(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDish(@PathVariable Long id) {
        Optional<Dish> dishOptional = dishService.findById(id);
        if (!dishOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        dishService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

//    @PostMapping
//    public ResponseEntity<Dish> saveDish(@ModelAttribute DishForm dishForm) {
//        MultipartFile img = dishForm.getImage();
//        if (img != null && img.getSize() != 0) {
//            String fileName = img.getOriginalFilename();
//            long currentTime = System.currentTimeMillis();
//            fileName = currentTime + "_" + fileName;
//            try {
//                FileCopyUtils.copy(img.getBytes(), new File(uploadPath + fileName));
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//            Dish dish = new Dish();
//            dish.setId(dishForm.getId());
//            dish.setName(dishForm.getName());
//            dish.setCategories(dishForm.getCategories());
//            dish.setPrice(dishForm.getPrice());
//            dish.setMerchant(dishForm.getMerchant());
//            dish.setDescription(dishForm.getDescription());
//            dish.setImage(fileName);
//            return new ResponseEntity<>(dishService.save(dish), HttpStatus.CREATED);
//        }
//        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//    }

    @PostMapping("/dish/create/merchant/{merchantId}")
    public ResponseEntity<?> saveDishImg(@ModelAttribute DishForm dishForm, @PathVariable Long merchantId) {
        MultipartFile img = dishForm.getImage();
        Dish newDish = new Dish();
        if (img != null && img.getSize() != 0) {
            String fileName = img.getOriginalFilename();
            long currentTime = System.currentTimeMillis();
            fileName = currentTime + fileName;
            newDish.setImage(fileName);
            try {
                FileCopyUtils.copy(img.getBytes(), new File(uploadPath + fileName));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Optional<Merchant> merchantOptional = this.merchantService.findMerchantByUser_Id(merchantId);
        if (!merchantOptional.isPresent()) {
            return new ResponseEntity<>(new ErrorMessage("ID cửa hàng không đúng"), HttpStatus.BAD_REQUEST);
        }
        newDish.setName(dishForm.getName());
        newDish.setPrice(dishForm.getPrice());
        newDish.setCategories(dishForm.getCategories());
        newDish.setDescription(dishForm.getDescription());
        newDish.setMerchant(merchantOptional.get());
        newDish.setSold(0L);
        return new ResponseEntity<>(dishService.save(newDish), HttpStatus.OK);
    }
    @GetMapping("/most-purchased/{top}")
    public ResponseEntity<?> getMostPurchasedDishes(@PathVariable Long top){
        if (top == null) top = 8L;
        Iterable<Dish> dishes = dishService.findMostPurchased(top.intValue());
        return new ResponseEntity<>(dishes, HttpStatus.OK);
    }
    @GetMapping("/merchants/{merchantId}")
    public ResponseEntity<?> getMerchantDishesByMerchantId(@PathVariable Long merchantId) {
        Optional<Merchant> findMerchant = merchantService.findById(merchantId);
        if (!findMerchant.isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Merchant merchant = findMerchant.get();

        Iterable<Dish> dishes = dishService.findAllByMerchant(merchant);
        return new ResponseEntity<>(dishes, HttpStatus.OK);
    }

    @GetMapping("/{dishId}/top-{limit}-same-category")
    public ResponseEntity<?> findDishesWithSameCategoryWith(@PathVariable Long dishId, @PathVariable int limit){
        Iterable<Dish> dishes =  dishService.findDishesWithSameCategoryWith(dishId, limit);
        return new ResponseEntity<>(dishes, HttpStatus.OK);
    }
}
