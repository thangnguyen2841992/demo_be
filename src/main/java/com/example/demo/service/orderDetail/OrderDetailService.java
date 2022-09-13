package com.example.demo.service.orderDetail;

import com.example.demo.model.entity.order.Order;
import com.example.demo.model.entity.order.OrderDetail;
import com.example.demo.repository.IOrderDetailRepository;
import com.example.demo.service.order.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailService implements IOrderDetailService {
    @Autowired
    private IOrderDetailRepository orderDetailRepository;
    @Autowired
    private IOrderService orderService;

    @Override
    public Iterable<OrderDetail> findAll() {
        return this.orderDetailRepository.findAll();
    }

    @Override
    public Optional<OrderDetail> findById(Long id) {
        return this.orderDetailRepository.findById(id);
    }

    @Override
    public OrderDetail save(OrderDetail orderDetail) {
        return this.orderDetailRepository.save(orderDetail);
    }

    @Override
    public void deleteById(Long id) {
        this.orderDetailRepository.deleteById(id);
    }

    @Override
    public Iterable<OrderDetail> findAllByOrder(Order order) {
        return this.orderDetailRepository.findAllByOrder(order);
    }

    @Override
    public Iterable<OrderDetail> findAllByDishId(Long id) {
        return this.orderDetailRepository.findAllByDishId(id);
    }

    @Override
    public Iterable<Order> findAllOrderByDishId(Long id) {
        List<OrderDetail> orderDetails = (List<OrderDetail>) findAllByDishId(id);
        List<Long> ordersIds = new ArrayList<>();
        for (OrderDetail orderDetail: orderDetails) {
            Long orderId = orderDetail.getOrder().getId();
            if (!ordersIds.contains(orderId)){
                ordersIds.add(orderId);
            }
        }
        List<Order> orders = new ArrayList<>();
        for (Long orderId : ordersIds) {
            Optional<Order> findOrder = orderService.findById(orderId);
            if (findOrder.isPresent()){
                orders.add(findOrder.get());
            }
        }

        return orders;
    }
}
