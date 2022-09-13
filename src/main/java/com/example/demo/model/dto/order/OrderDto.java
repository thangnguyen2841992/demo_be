package com.example.demo.model.dto.order;

import com.example.demo.model.dto.cart.CartDto;
import com.example.demo.model.entity.merchant.Merchant;
import com.example.demo.model.entity.order.DeliveryInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private Long id;
    private CartDto cart;
    private DeliveryInfo deliveryInfo;
    private String noteRestaurant;
    private String noteShipper;
    private Merchant merchant;
    private Date createDate;
    private int status;
}
