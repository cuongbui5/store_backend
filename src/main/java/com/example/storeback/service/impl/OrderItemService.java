package com.example.storeback.service.impl;

import com.example.storeback.dto.CartItemDto;
import com.example.storeback.model.CartItem;
import com.example.storeback.model.Order;
import com.example.storeback.model.OrderItem;
import com.example.storeback.repository.OrderItemRepository;
import com.example.storeback.service.IOrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderItemService implements IOrderItemService {
    private final OrderItemRepository orderItemRepository;
    @Override
    public OrderItem createOrderItem(CartItem cartItem, Order order) {
        OrderItem orderItem=new OrderItem();
        orderItem.setOrder(order);
        orderItem.setProduct(cartItem.getProduct());
        orderItem.setQuantity(cartItem.getQuantity());
        orderItem.setTotalPrice(cartItem.getTotalPrice());
        return orderItemRepository.save(orderItem);
    }
}
