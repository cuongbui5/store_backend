package com.example.storeback.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCartItem {
    private Long productId;
    private Long userId;
    private int quantity;


}
