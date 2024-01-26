package com.example.storeback.dto.request;


import com.example.storeback.model.enums.OrderStatus;
import lombok.Data;


@Data
public class CreateOrderRequest {
    private Long userId;
    private OrderStatus status;
    private Double shippingCost;
    private Double total;

}
