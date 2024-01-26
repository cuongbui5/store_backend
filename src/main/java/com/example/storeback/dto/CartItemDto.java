package com.example.storeback.dto;

import com.example.storeback.model.Product;
import lombok.Data;

@Data
public class CartItemDto {
    private Long id;
    private Product product;
    private int quantity;
    private double total;
}
