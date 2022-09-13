package com.example.demo.controller.order;
import com.example.demo.model.dto.ErrorMessage;
import com.example.demo.model.dto.order.OrderDto;
import com.example.demo.model.entity.auth.User;
import com.example.demo.model.entity.order.Order;
import com.example.demo.service.cart.CartService;
import com.example.demo.service.order.IOrderService;
import com.example.demo.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("current-user")
public class CreateOrderController {
    @Autowired
   private IUserService userService;

    @Autowired
    private  IOrderService orderService;

    @Autowired
    private   CartService cartService;

    // Input 1 orderDto => tạo vào lưu order vào DB
    @PostMapping("/create-order/user/{userId}")
    public ResponseEntity<?> createOrder(@RequestBody OrderDto orderDto, @PathVariable Long userId) {
//        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> userOptional = userService.findById(userId);
        if (!userOptional.isPresent()) {
            return new ResponseEntity<>(new ErrorMessage("Tài khoản không tồn tại"), HttpStatus.BAD_REQUEST);
        }
        User currentUser = userOptional.get();

        if (orderDto.getCart().getCartDetails().size() == 0) {
            ErrorMessage errorMessage = new ErrorMessage("Giỏ hàng trống");
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        Order order = cartService.saveOrderToDB(currentUser, orderDto);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }


}
