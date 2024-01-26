package com.example.storeback.service.impl;

import com.example.storeback.dto.CartItemDto;
import com.example.storeback.dto.request.CreateCartItem;
import com.example.storeback.dto.response.CartResponse;
import com.example.storeback.exception.NotFound;
import com.example.storeback.model.Cart;
import com.example.storeback.model.CartItem;
import com.example.storeback.model.Product;
import com.example.storeback.repository.CartItemRepository;
import com.example.storeback.service.ICartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService {
    private final CartItemRepository cartItemRepository;
    private final ProductService productService;
    private final CartService cartService;


    @Override
    public CartItem createCartItem(CreateCartItem createCartItem) {
        Cart cart=cartService.getCartByUserId(createCartItem.getUserId());
        Product product=productService.getProductById(createCartItem.getProductId());
        if(cart==null||product==null){
            throw new RuntimeException("Can not create cart item!");
        }
        List<CartItem> cartItems=cartItemRepository.findAllByCartId(cart.getId());
        if(!cartItems.isEmpty()){
            cartItems.forEach(c->{
                if(Objects.equals(c.getProduct().getId(), product.getId())){
                    throw new RuntimeException("Product has already add to cart!");
                }
            });
        }
        CartItem cartItem=new CartItem();
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setQuantity(createCartItem.getQuantity());
        cartItem.setTotalPrice(product.getPrice()*createCartItem.getQuantity());
        return cartItemRepository.save(cartItem);




    }
    public CartResponse getCartItemByUserId(Long userId){
        Cart cart=cartService.getCartByUserId(userId);
        List<CartItem> cartItems=cartItemRepository.findAllByCartId(cart.getId());
        AtomicReference<Double> total= new AtomicReference<>(0.0);
        List<CartItemDto> results=new ArrayList<>();
        if(!cartItems.isEmpty()){
            cartItems.forEach(c->{
                CartItemDto cartItemDto=new CartItemDto();
                cartItemDto.setId(c.getId());
                cartItemDto.setProduct(c.getProduct());
                cartItemDto.setQuantity(c.getQuantity());
                cartItemDto.setTotal(c.getTotalPrice());
                total.updateAndGet(v -> (v + c.getTotalPrice()));
                results.add(cartItemDto);
            });
        }
        CartResponse cartResponse=new CartResponse();
        cartResponse.setId(cart.getId());
        cartResponse.setCartItemDtos(results);
        cartResponse.setTotal(total.get());


        return cartResponse;
    }

    @Override
    public CartItem updateCartItem(int quantity, Long cartItemId) {
        Optional<CartItem> cartItem=cartItemRepository.findById(cartItemId);
        if(cartItem.isEmpty()){
            throw new NotFound("Not found cart item with id:"+cartItem);
        }
        cartItem.get().setQuantity(quantity);
        cartItem.get().setTotalPrice(quantity*cartItem.get().getProduct().getPrice());

        return cartItemRepository.save(cartItem.get());
    }

    @Override
    public void deleteCartItem(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }
}
