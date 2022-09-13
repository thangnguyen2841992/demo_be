package com.example.demo.repository;

import com.example.demo.model.entity.order.Order;
import com.example.demo.model.entity.order.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    Iterable<OrderDetail> findAllByOrder(Order order);
    Iterable<OrderDetail> findAllByDishId(Long id);
}
