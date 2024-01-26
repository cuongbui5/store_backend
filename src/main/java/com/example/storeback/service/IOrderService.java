package com.example.storeback.service;

import com.example.storeback.dto.request.CreateOrderRequest;
import com.example.storeback.model.Order;

import java.util.List;

public interface IOrderService {
    Order createOrder(CreateOrderRequest createOrderRequest);
    List<Order> getOrderByUserId(Long userId);


}
