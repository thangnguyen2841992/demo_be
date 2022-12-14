package com.example.demo.model.entity.merchant;

import com.example.demo.model.entity.auth.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "merchant_register_request")
public class MerchantRegisterRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;
    @NotEmpty(message = "Vui lòng nhập tên cửa hàng")
    private String name;
    @NotEmpty(message = "Vui lòng nhập tên cửa hàng")
    private String description;
    @NotEmpty(message = "Vui lòng nhập tên cửa hàng")
    private String address;
    @NotEmpty(message = "Vui lòng nhập tên cửa hàng")
    @Pattern(regexp = "^[0](\\+\\d{1,3}\\s?)?((\\(\\d{3}\\)\\s?)|(\\d{3})(\\s|-?))(\\d{3}(\\s|-?))(\\d{3})(\\s?(([E|e]xt[:|.|]?)|x|X)(\\s?\\d+))?"
    )
    private String phone;
    private String openTime;
    private String closeTime;
    @Column(columnDefinition = "boolean default false")
    private boolean reviewed;
    @Column(columnDefinition = "boolean default false")
    private boolean accept;

}
