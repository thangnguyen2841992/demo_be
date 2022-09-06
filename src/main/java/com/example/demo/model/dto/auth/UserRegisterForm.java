package com.example.demo.model.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterForm {
    private Integer role;

    @NotEmpty(message = "Không được để trống")
    @Column(unique = true)
    private String email;
    @NotEmpty(message = "Không được để trống")
    @Column(unique = true)
    private String username;
    private String password;
    private String confirmPassword;

    private String fullName;

    private String address;



    public boolean confirmPasswordMatch(){
        return password.equals(confirmPassword);
    }
}
