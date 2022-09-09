package com.example.demo.model.dto;

import com.example.demo.model.entity.category.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchForm {
    private String q;
    private List<Category> categories = new ArrayList<>();
    private int limit;
}
