package com.example.demo.service.order;

import com.example.demo.model.dto.cart.CartDetailDto;
import com.example.demo.model.dto.cart.CartDto;
import com.example.demo.model.dto.order.OrderDto;
import com.example.demo.model.dto.order.OrderDtoByOwner;
import com.example.demo.model.entity.merchant.Merchant;
import com.example.demo.model.entity.order.Order;
import com.example.demo.model.entity.order.OrderDetail;
import com.example.demo.repository.IOrderRepository;
import com.example.demo.service.orderDetail.IOrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class OrderService implements IOrderService{
    @Autowired
    private IOrderRepository orderRepository;
    @Autowired
    private IOrderDetailService orderDetailService;
    @Override
    public Iterable<Order> findAll() {
        return this.orderRepository.findAll();
    }

    @Override
    public Optional<Order> findById(Long id) {
        return this.orderRepository.findById(id);
    }

    @Override
    public Order save(Order order) {
        return this.orderRepository.save(order);
    }

    @Override
    public void deleteById(Long id) {
        this.orderRepository.deleteById(id);
    }

    @Override
    public OrderDto getOrderDto(Long orderId) {
        Optional<Order> findOrder = findById(orderId);
        if (!findOrder.isPresent()) {
            return null;
        }

        Order order = findOrder.get();
        OrderDto orderDto = new OrderDto();
        orderDto.setId(orderId);
        orderDto.setDeliveryInfo(order.getDeliveryInfo());
        orderDto.setStatus(order.getStatus());

        CartDto cartDto = new CartDto();
        Iterable<OrderDetail> orderDetails = orderDetailService.findAllByOrder(order);
        List<OrderDetail> orderDetailList =
                StreamSupport.stream(orderDetails.spliterator(), false)
                        .collect(Collectors.toList());
        for (OrderDetail orderDetail : orderDetailList) {
            CartDetailDto cartDetailDto = new CartDetailDto(orderDetail.getDish(), orderDetail.getQuantity());
            cartDto.addCartDetailDto(cartDetailDto);
        }
        orderDto.setCart(cartDto);

        Merchant merchant = orderDetailList.get(0).getDish().getMerchant();
        orderDto.setMerchant(merchant);
        orderDto.setCreateDate(order.getCreateDate());
        return orderDto;
    }

    @Override
    public List<OrderDto> findAllOrderDtoByUserId(Long userId) {

        Iterable<Order> orders = orderRepository.findAllByUser_IdOrderByCreateDateDesc(userId);
        List<OrderDto> orderDtos = new ArrayList<>();

        for (Order order : orders) {
            OrderDto orderDto = getOrderDto(order.getId());
            orderDtos.add(orderDto);
        }

        return orderDtos;
    }

    @Override
    public Iterable<Order> findAllByUserId(Long id) {
        return orderRepository.findAllByUser_IdOrderByCreateDateDesc(id);
    }

    @Override
    public Iterable<OrderDtoByOwner> findAllOrderDtoByOwnerId(Long ownerId) {
        return orderRepository.findOrderByOwnerIdOrderByCreateDateDesc(ownerId);
    }
}
