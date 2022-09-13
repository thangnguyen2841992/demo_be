package com.example.demo.model.dto.cart;

import com.example.demo.model.entity.dish.Dish;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDetailDto {
    private Dish dish;
    private int quantity;
}
