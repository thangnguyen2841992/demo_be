package com.example.demo.service.orderDetail;

import com.example.demo.model.entity.order.Order;
import com.example.demo.model.entity.order.OrderDetail;

import java.util.Optional;

public interface IOrderDetailService {
    Iterable<OrderDetail> findAll();

    Optional<OrderDetail> findById(Long id);

    OrderDetail save(OrderDetail orderDetail);

    void deleteById(Long id);

    Iterable<OrderDetail> findAllByOrder(Order order);
    Iterable<OrderDetail> findAllByDishId(Long id);
    Iterable<Order> findAllOrderByDishId(Long id);
}
