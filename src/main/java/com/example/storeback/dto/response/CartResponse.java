package com.example.storeback.dto.response;

import com.example.storeback.dto.CartItemDto;
import com.example.storeback.model.CartItem;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
public class CartResponse {
    private Long id;
    private List<CartItemDto> cartItemDtos;
    private Double total;
}
