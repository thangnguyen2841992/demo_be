package com.example.demo.service.order;

import com.example.demo.model.dto.order.OrderDto;
import com.example.demo.model.dto.order.OrderDtoByOwner;
import com.example.demo.model.entity.auth.Role;
import com.example.demo.model.entity.order.Order;

import java.util.List;
import java.util.Optional;

public interface IOrderService {
    Iterable<Order> findAll();

    Optional<Order> findById(Long id);

    Order save(Order order);

    void deleteById(Long id);

    OrderDto getOrderDto(Long orderId);

    List<OrderDto> findAllOrderDtoByUserId(Long userId);

    Iterable<Order> findAllByUserId(Long id);

    Iterable<OrderDtoByOwner> findAllOrderDtoByOwnerId(Long ownerId);
}
