package com.example.storeback.service;

import com.example.storeback.dto.request.CreateCartItem;
import com.example.storeback.model.CartItem;

public interface ICartItemService {
    CartItem createCartItem(CreateCartItem createCartItem);
    CartItem updateCartItem(int quantity,Long cartItemId);

    void deleteCartItem(Long cartItemId);
}
