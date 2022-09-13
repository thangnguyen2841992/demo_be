package com.example.demo.service.deliveryInfo;

import com.example.demo.model.entity.auth.User;
import com.example.demo.model.entity.category.Category;
import com.example.demo.model.entity.order.DeliveryInfo;

import java.util.Optional;

public interface IDeliveryInfoService {
    Iterable<DeliveryInfo> findAll();

    Optional<DeliveryInfo> findById(Long id);

    DeliveryInfo save(DeliveryInfo deliveryInfo);

    void deleteById(Long id);

    Optional<DeliveryInfo> findDefaultDeliveryInfo(User user);

    Iterable<DeliveryInfo> findOtherDeliveryInfos(User user);

    boolean setDeliveryInfoToSelected(Long userId, Long deliveryInfoId);
}
