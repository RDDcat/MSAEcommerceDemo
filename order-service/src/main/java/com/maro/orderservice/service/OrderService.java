package com.maro.orderservice.service;

import com.maro.orderservice.dto.OrderDTO;
import com.maro.orderservice.repository.OrderEntity;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {
    OrderDTO createOrder(OrderDTO orderDetails);
    OrderDTO getOrderByOrderId(String orderId);
    Iterable<OrderEntity> getOrderByUserId(String userId);
}
