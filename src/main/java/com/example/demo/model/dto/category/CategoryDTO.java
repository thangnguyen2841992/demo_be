package com.example.demo.model.dto.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryDTO {
    private Long id;
    private String name;
    private int numberOfDishes;
}
