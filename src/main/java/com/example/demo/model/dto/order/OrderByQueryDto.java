package com.example.demo.model.dto.order;


import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public interface OrderByQueryDto {
    Long getId ();
    LocalDate getCreate_Date();
    double getDiscount_Amount();
    String getRestaurant_Note();
    double getService_Fee();
    double getShipping_Fee();
    String getShipping_Note();
    double getTotal_Fee();
    Long getUser_Id();
    String getFull_Name ();
    String getPhone ();
    String getAddress ();
    String getEmail ();
}
