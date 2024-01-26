package com.example.storeback.service.impl;

import com.example.storeback.dto.request.CreateCartItem;
import com.example.storeback.exception.NotFound;
import com.example.storeback.model.Cart;
import com.example.storeback.model.Category;
import com.example.storeback.model.User;
import com.example.storeback.repository.CartRepository;
import com.example.storeback.repository.UserRepository;
import com.example.storeback.service.ICartService;
import com.example.storeback.service.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;




    @Override
    public Cart createCart(Long userId) {
        Optional<User> user=userRepository.findUserById(userId);
        if(user.isEmpty()){
            throw new NotFound("Not found user with id:"+userId);
        }
        Cart cart=new Cart();
        cart.setUser(user.get());
        return cartRepository.save(cart);

    }



    @Override
    public Cart getCartByUserId(Long userId) {
        if(!cartRepository.existsCartByUserId(userId)){
           return createCart(userId);

        }
        return cartRepository.findByUserId(userId).get(0);
    }

    @Override
    public Cart getCartById(Long cartId) {
        Optional<Cart> cart=cartRepository.findById(cartId);
        if(cart.isEmpty()){
            throw new NotFound("Not found cart with id:"+cartId);
        }
        return cart.get();
    }

    @Override
    public boolean addToCart(CreateCartItem cartItem) {
        return false;
    }
}
