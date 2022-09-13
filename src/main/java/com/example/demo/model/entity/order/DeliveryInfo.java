package com.example.demo.model.entity.order;

import com.example.demo.model.entity.auth.User;
import com.example.demo.model.entity.merchant.Merchant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "delivery_info")
public class DeliveryInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String address;

    @NotEmpty
    private String phone;

    @ManyToOne
    private User user;

    @Column(columnDefinition = "BIT default 0")
    private boolean selected;
}
