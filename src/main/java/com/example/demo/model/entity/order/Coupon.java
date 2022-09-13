package com.example.demo.model.entity.order;

import com.example.demo.model.entity.merchant.Merchant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "coupons")
public class Coupon {
    public static final String FLAT = "FLAT";
    public static final String PERCENT = "PERCENT";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @ManyToOne
    private Merchant merchant;

    private String type;

    private double value;


}
