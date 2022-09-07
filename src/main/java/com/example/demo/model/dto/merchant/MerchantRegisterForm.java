package com.example.demo.model.dto.merchant;

import com.example.demo.model.entity.auth.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MerchantRegisterForm {
    private User user;
    private String name;
    private String description;
    private String address;
    private String phone;
    private String openTime;
    private String closeTime;
}
