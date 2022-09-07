package com.example.demo.model.entity.dish;

import com.example.demo.model.entity.merchant.Merchant;
import com.example.demo.model.entity.category.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "dishes")
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "VARCHAR(100)", nullable = false) // Xử lý ở tầng Data Layer
    @NotNull
    private String name;

    @Column(nullable = false)
    @NotNull
    private double price;

    @ManyToMany
    private List<Category> categories;

    @ManyToOne
    private Merchant merchant;

    @Column (columnDefinition = "BIGINT default 0")
    private Long sold;

    @Column(columnDefinition = "varchar(1000)")
    private String description;

    @NotNull
    @Column(columnDefinition = "varchar(1000) default 'dish-default.jpg'")
    private String image;
}
