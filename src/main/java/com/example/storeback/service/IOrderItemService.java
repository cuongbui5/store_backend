package com.example.storeback.service;

import com.example.storeback.dto.CartItemDto;
import com.example.storeback.model.CartItem;
import com.example.storeback.model.Order;
import com.example.storeback.model.OrderItem;

public interface IOrderItemService {
    OrderItem createOrderItem(CartItem cartItem, Order order);

}
