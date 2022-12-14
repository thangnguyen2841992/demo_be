package com.example.demo.model.dto.order;

import java.util.Date;

public interface OrderDtoByOwner {
    Long getId();

    Date getCreate_Date();

    String getFull_Name();

    String getName();

    double getOrderPrice();

    String getAddress();

    String getPhone();

    int getStatus();
}
