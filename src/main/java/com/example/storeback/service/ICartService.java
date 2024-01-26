package com.example.storeback.service;

import com.example.storeback.dto.request.CreateCartItem;
import com.example.storeback.model.Cart;

public interface ICartService {
    Cart createCart(Long userId);
    Cart getCartByUserId(Long userId);
    Cart getCartById(Long cartId);

    boolean addToCart(CreateCartItem cartItem);
}
