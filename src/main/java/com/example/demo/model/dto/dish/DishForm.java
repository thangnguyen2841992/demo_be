package com.example.demo.model.dto.dish;

import com.example.demo.model.entity.Merchant;
import com.example.demo.model.entity.category.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DishForm {
    private Long id;

    private String name;

    private double price;

    private List<Category> categories;

    private Merchant merchant;

    private String description;

    private MultipartFile image;

    private boolean ceased;
}
